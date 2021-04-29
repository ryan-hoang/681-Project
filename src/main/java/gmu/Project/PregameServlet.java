package gmu.Project;

import gmu.Project.model.Game;
import gmu.Project.repository.GameRepository;
import gmu.Project.repository.UserRepository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PregameServlet extends HttpServlet
{
    //Get page waiting for players to join
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String requestType = request.getParameter("requestType");
        String username = request.getParameter("username");
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        UserRepository userRepo = (UserRepository) springContext.getBean("userRepository");
        GameRepository gameRepo = (GameRepository) springContext.getBean("gameRepository");


        if(requestType.equals("createGame"))//Receive request from user to create game. Show user the lobby page.
        {
            Game g = new Game();
            g.setGameOwner(username);
            g.setStatus(Status.PREGAME);

        }
        else if(requestType.equals("joinGame"))
        {

        }




        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // send HTML page to client
        out.println("<html>");
        out.println("<head><title>Waiting for Players</title></head>");
        out.println("<script> setTimeout(function(){window.location.reload(1);}, 5000); </script>");
        out.println("<body>");
        out.println("");
        out.println("</body></html>");
    }

    //Start game, forward to game servlet.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("game");
        requestDispatcher.forward(req,resp);
    }
}


//from homepage create game -> here
//from homepage join game -> here