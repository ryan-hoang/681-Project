package gmu.Project;

import gmu.Project.model.User;
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

        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        UserRepository userRepo = (UserRepository) springContext.getBean("userRepository");
        User user = userRepo.findByUsername("admin");
        //System.out.println(java.util.Calendar.getInstance().getTime());

        GameBean gb = new GameBean();
        gb.setGs(GameState.SHOWHAND);
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

        Card a = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        Card k = new Card(Card.Value.KING, Card.Suit.CLUBS);
        Card q = new Card(Card.Value.QUEEN, Card.Suit.CLUBS);
        Card j = new Card(Card.Value.JACK, Card.Suit.CLUBS);
        Card t = new Card(Card.Value.TEN, Card.Suit.CLUBS);
        Card[] h = new Card[5];
        h[0] = a;
        h[1] = k;
        h[2] = q;
        h[3] = j;
        h[4] = t;
        gb.setOpponentHand(h);

        gb.setGameWinner("Alex");
        gb.setWinningHand(Hand.ROYALFLUSH);

        gb.setUser("admin");
        gb.setUserTurn("admin");

        HttpSession session = request.getSession();
        session.setAttribute("gamebean", gb);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("table");
        requestDispatcher.forward(request,response);
    }
}
