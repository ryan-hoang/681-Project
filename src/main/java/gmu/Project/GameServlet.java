package gmu.Project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class GameServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        /*
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // send HTML page to client
        out.println("<html>");
        out.println("<head><title>A Test Servlet</title></head>");
        out.println("<body>");
        out.println("<h1>Test</h1>");
        out.println("<p>Simple servlet for testing.</p>");
        out.println("</body></html>");
        */

        GameBean gb = new GameBean();
        gb.setGs(GameState.BET);
        Card ace = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        Card two = new Card(Card.Value.TWO, Card.Suit.HEARTS);
        Card three = new Card(Card.Value.THREE, Card.Suit.DIAMONDS);
        Card four = new Card(Card.Value.FOUR, Card.Suit.SPADES);
        Card king = new Card(Card.Value.KING, Card.Suit.HEARTS);

        Card[] hand = new Card[5];
        hand[0] = ace;
        hand[1] = two;
        hand[2] = three;
        hand[3] = four;
        hand[4] = king;

        gb.setHand(hand);
        HttpSession session = request.getSession();
        session.setAttribute("gamebean", gb);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("table");
        requestDispatcher.forward(request,response);
    }
}
