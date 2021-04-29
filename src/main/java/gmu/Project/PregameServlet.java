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
import java.io.PrintWriter;


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
                if(join == null)
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

    //Start game, forward to game servlet.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("game");
        requestDispatcher.forward(req,resp);
    }

    private void printPage(HttpServletResponse response)
    {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n" +
                    "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Pregame Lobby</title>\n" +
                    "</head>\n" +
                    "<script> setTimeout(function(){window.location.reload(1);}, 5000); </script>\n" +
                    "<style>\n" +
                    "    .box{\n" +
                    "        margin:0 auto;\n" +
                    "        width:350px;\n" +
                    "        padding:20px;\n" +
                    "        background:#f9f9f9;\n" +
                    "        border:2px solid #333;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "<body>\n" +
                    "\n" +
                    "<div class=\"box\">\n" +
                    "    <form action=\"/game\">\n" +
                    "        <div th:if=\"${#strings.equals(session.game.getGameOwner(),session.username)}\">\n" +
                    "            <div th:if=\"${session.game.getP2username() == ''}\">\n" +
                    "                <h2>Waiting For Players...</h2>\n" +
                    "                <h3 th:text=\"'Player 1:' +${session.game.getP1username()}\"></h3>\n" +
                    "                <h3 th:text=\"'Player 2:' +${session.game.getP2username()}\"></h3>\n" +
                    "            </div>\n" +
                    "            <div th:unless=\"${session.game.getP2username() == ''}\">\n" +
                    "                <h3 th:text=\"'Player 1:' +${session.game.getP1username()}\"></h3>\n" +
                    "                <h3 th:text=\"'Player 2:' +${session.game.getP2username()}\"></h3>\n" +
                    "                <button type=\"submit\">Start Game</button>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div th:unless=\"${#strings.equals(session.game.getGameOwner(),session.username)}\">\n" +
                    "            <h2>Waiting For Host to Start Game.</h2>\n" +
                    "            <h3 th:text=\"'Player 1:' + ${session.game.getP1username()}\"></h3>\n" +
                    "            <h3 th:text=\"'Player 2:' + ${session.game.getP2username()}\"></h3>\n" +
                    "        </div>\n" +
                    "    </form>\n" +
                    "</div>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");
        }
        catch(IOException e)
        {
            System.err.println("Failed to print out Pregame Page.");
        }
    }
}


//from homepage create game -> here
//from homepage join game -> here