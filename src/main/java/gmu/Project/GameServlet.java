package gmu.Project;

import gmu.Project.model.Game;
import gmu.Project.model.User;
import gmu.Project.repository.GameRepository;
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
    //Notes:all forms on the table.html page should be post calls, the page refreshes and thats a get call

    //regular get calls come here and we just pull the latest bean and redirect to table
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Construct game bean and redirect to table


        /*
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
 */
    }


    //Form moves come here, we make the necessary adjustments to the game db for each move type.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //manipulate game state -> Construct game bean -> redirect to table

        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        GameRepository gameRepo = (GameRepository) springContext.getBean("gameRepository");

        String requestType = request.getParameter("requestType");
        String ID = request.getParameter("gameID");
        Long gameID = Long.parseLong(ID);
        Game game = gameRepo.findByGameId(gameID);

        switch(requestType)
        {
            case "start": // form action from the pregame lobby start button
                break;
            case "betone": //form action from first round bet in table.html
                break;
            case "draw": // form action from draw form in table.html
                break;
            case "bettwo":// form action from second round bet in table.html
                break;
            case "showhand":// form action, we have a form thats just an ok button for both players after both hands are shown on table.html
                //check if anyone is broke here and end game by sending a bean with the gameover state.
                break;
            case "gameover": // form acton, ok button to end game, cleanup game and exit to homepage
                break;

        }
    }

    private GameState getNextState(GameState gs)
    {
        //Deal/BET round1 -> Draw/discard cards -> BET round 2 -> Show hands
        switch(gs)
        {
            case BETONE:
                return GameState.DRAW;
            case DRAW:
                return GameState.BETTWO;
            case SHOWHAND:
                return GameState.BETONE;
            case BETTWO:
                return GameState.SHOWHAND;
            default:
                return GameState.GAMEOVER;
        }
    }
}
