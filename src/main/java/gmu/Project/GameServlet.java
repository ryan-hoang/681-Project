package gmu.Project;

import gmu.Project.model.Game;
import gmu.Project.model.GameMove;
import gmu.Project.repository.GameRepository;
import gmu.Project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class GameServlet extends HttpServlet
{
    //Notes:all forms on the table.html page should be post calls, the page refreshes and thats a get call

    //regular get calls come here and we just pull the latest bean and redirect to table
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Construct game bean and redirect to table
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        GameRepository gameRepo = (GameRepository) springContext.getBean("gameRepository");
        UserRepository userRepo = (UserRepository) springContext.getBean("userRepository");

        String requestType = request.getParameter("requestType");

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails)
        {username = ((UserDetails)principal).getUsername();}
        else
        {username = principal.toString();}


        Long gameID = userRepo.findByUsername(username).getCurrentGame();
        Game game = gameRepo.findByGameId(gameID);
        Deck deck = new Deck(game.getDeck().toArray(new Card[0]));

        goToTable(game,username,request,response);

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
        Deck deck = new Deck(game.getDeck().toArray(new Card[0]));

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails)
        {username = ((UserDetails)principal).getUsername();}
        else
        {username = principal.toString();}


        switch(requestType)
        {
            case "start": // form action from the pregame lobby start button
                game.setStatus(Status.ACTIVE);
                game.setState(GameState.BETONE);
                ArrayList<Card> p1Hand = deck.deal(5);
                ArrayList<Card> p2Hand = deck.deal(5);
                game.setP1Hand(p1Hand);
                game.setP2Hand(p2Hand);
                game.setPrevP1Bet(0);
                game.setPrevP2Bet(0);
                game.setCurrentPot(0);
                game.setHandTurn(0);
                gameRepo.save(game);

                goToTable(game,username,request,response);
                break;
            case "BETONE": //form action from first round bet in table.html
                String s = request.getParameter("betamount");
                if(s.equals("")) {
                    goToTable(game, username, request, response);
                    break;
                }
                int betAmount = Integer.parseInt(s);
                if(username.equals(game.getP1username())) {
                    if (betAmount < 0 || betAmount + game.getPrevP1Bet() < game.getPrevP2Bet() || betAmount > game.getP1balance()) {
                        //throw error "Call or Raise"
                        goToTable(game, username, request, response);
                        break;
                    }
                    if(betAmount + game.getPrevP1Bet() == game.getPrevP2Bet() && game.getHandTurn() != 0){ //Check to see if they are calling or "Checking" both bet 0
                        game.setLastMove(username + " called!");
                        game.setMessage(username + " called!");
                        game.setLastMoveTime(LocalDateTime.now());
                        game.setState(getNextState(game.getState()));
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
                    game.setCurrentPot(game.getCurrentPot() + betAmount);
                    game.setP1balance(game.getP1balance() - betAmount); //Update p1 balance
                    game.setTurn(game.getP2username()); //Changing turns
                    game.setLastMove(username + " bet $" + betAmount);
                    game.setMessage(username + " bet $" + betAmount);
                    game.setLastMoveTime(LocalDateTime.now());
                    game.setPrevP1Bet(betAmount + game.getPrevP1Bet()); //Previous bet for checking
                    game.setHandTurn(game.getHandTurn() + 1);
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                if(username.equals(game.getP2username())) {
                    if (betAmount < 0 || betAmount + game.getPrevP2Bet() < game.getPrevP1Bet() || betAmount > game.getP2balance()) {
                        //throw error "Call or Raise"
                        goToTable(game, username, request, response);
                        break;
                    }
                    if(betAmount + game.getPrevP2Bet() == game.getPrevP1Bet()){  //Check to see if they are calling or "Checking" both bet 0
                        game.setLastMove(username + " called!");
                        game.setMessage(username + " called!");
                        game.setLastMoveTime(LocalDateTime.now());
                        game.setState(getNextState(game.getState()));
                        game.setHandTurn(game.getHandTurn() + 1);
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
                    game.setCurrentPot(game.getCurrentPot() + betAmount);
                    game.setP2balance(game.getP2balance() - betAmount); //Update p2 balance
                    game.setTurn(game.getP1username()); //Changing turns
                    game.setLastMove(username + " bet $" + betAmount);
                    game.setMessage(username + " bet $" + betAmount);
                    game.setLastMoveTime(LocalDateTime.now());
                    game.setPrevP2Bet(betAmount + game.getPrevP2Bet()); //Previous bet for checking
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                break;
            case "DRAW": // form action from draw form in table.html
                break;
            case "BETTWO":// form action from second round bet in table.html
                break;
            case "SHOWHAND":// form action, we have a form thats just an ok button for both players after both hands are shown on table.html
                //check if anyone is broke here and end game by sending a bean with the gameover state.
                break;
            case "GAMEOVER": // form acton, ok button to end game, cleanup game and exit to homepage
                break;
            case "FOLD":
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

    private GameBean generateLatestBean(Game game,String username)
    {
        GameBean gb = new GameBean();
        gb.setHandWinner(game.getHandWinner());
        gb.setWinningHand(game.getWinningHand());
        gb.setGameID(game.getGameId());
        gb.setCurrentPot(game.getCurrentPot());
        String msg = game.getMessage();
        gb.setMessage(msg == null ? "" : msg);
        if(game.getP1username().equals(username))
        {

            gb.setOpponentHand(game.getP1Hand().toArray(new Card[0]));  //them
            gb.setHand(game.getP1Hand().toArray(new Card[0]));          //me
            gb.setMyMoney(game.getP1balance());                         //me
            gb.setOpponentMoney(game.getP2balance());                   //them
            gb.setUser(game.getP1username());                           //me
            gb.setOpponent(game.getP2username());                       //them
        }
        else
        {
            gb.setOpponentHand(game.getP1Hand().toArray(new Card[0]));  //them
            gb.setHand(game.getP2Hand().toArray(new Card[0]));          //me
            gb.setMyMoney(game.getP2balance());                         //me
            gb.setOpponentMoney(game.getP1balance());                   //them
            gb.setUser(game.getP2username());                           //me
            gb.setOpponent(game.getP1username());                       //them
        }

        gb.setUserTurn(game.getTurn());

        gb.setGs(game.getState());

        gb.setGameWinner(game.getGameWinner());

        gb.setLastMove(game.getLastMove());

        return gb;
    }

    private void goToTable(Game game, String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GameBean gb = generateLatestBean(game,username);
        HttpSession session = request.getSession();
        session.setAttribute("gamebean", gb);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("table");
        requestDispatcher.forward(request,response);
    }

}
