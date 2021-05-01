package gmu.Project;

import gmu.Project.model.Game;
import gmu.Project.model.User;
import gmu.Project.repository.GameRepository;
import gmu.Project.repository.LogRepository;
import gmu.Project.repository.UserRepository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class PregameServlet extends HttpServlet
{
    //Get page waiting for players to join
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int STARTING_BALANCE = 10;

        String requestType = request.getParameter("requestType");
        String username = request.getParameter("username");
        Long gameID;
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        UserRepository userRepo = (UserRepository) springContext.getBean("userRepository");
        GameRepository gameRepo = (GameRepository) springContext.getBean("gameRepository");
        LogRepository logRepo = (LogRepository) springContext.getBean("logRepository");
        Game join;
        boolean inGame = userRepo.findByUsername(username).isInGame();
        User user = userRepo.findByUsername(username);

        if(inGame)
        {
            Game gm;
            do {
                System.out.println("getting new game instance from db GameID:" + user.getCurrentGame());
                gm = gameRepo.findByGameId(user.getCurrentGame());
            }while(gm ==null);
            HttpSession session = request.getSession();
            session.setAttribute("game", gm);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("lobby");
            requestDispatcher.forward(request,response);
            System.out.println("InGame Branch");
        }
        else
        {
            if (requestType.equals("createGame"))//Receive request from user to create game. Show user the lobby page.
            {
                System.out.println("Create Game Branch");
                if(gameRepo.hasPendingGame(username) == 0) {
                    Game g = new Game();
                    g.setGameOwner(username);
                    g.setP1username(username);
                    g.setP1balance(STARTING_BALANCE);
                    g.setP2balance(STARTING_BALANCE);
                    g.setTurn(username);
                    g.setStatus(Status.PREGAME);
                    g.setDeck(new Deck().getDeck());
                    gameRepo.save(g);
                }

                Game tmp = gameRepo.getMyPendingGame(username);
                user.setCurrentGame(tmp.getGameId());
                user.setInGame(true);
                userRepo.save(user);

                HttpSession session = request.getSession();
                session.setAttribute("game", tmp);
                session.setAttribute("username", username);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("lobby");
                requestDispatcher.forward(request,response);
            }
            else if (requestType.equals("joinGame"))
            {
                gameID = Long.parseLong(request.getParameter("games"));
                join = gameRepo.findByGameId(gameID);
                if(join == null || user.isInGame())
                {
                    response.sendRedirect(request.getContextPath() + "/homepage");
                    return;
                }
                join.setP2username(username);
                gameRepo.save(join);
                user.setCurrentGame(gameID);
                user.setInGame(true);
                userRepo.save(user);

                HttpSession session = request.getSession();
                session.setAttribute("game", join);
                session.setAttribute("username", username);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("lobby");
                requestDispatcher.forward(request,response);
            }
        }
    }

}


//from homepage create game -> here
//from homepage join game -> here