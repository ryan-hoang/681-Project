package gmu.Project;

import gmu.Project.model.Game;
import gmu.Project.model.GameMove;
import gmu.Project.model.User;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static gmu.Project.HandComparison.compareHands;
import static gmu.Project.HandComparison.determineHand;


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

        if(userRepo.findByUsername(username).isInGame()) {
            Long gameID = userRepo.findByUsername(username).getCurrentGame();
            Game game = gameRepo.findByGameId(gameID);
            LocalDateTime lastMove = game.getLastMoveTime();
            Duration timeout = Duration.between(lastMove, LocalDateTime.now());
            if(timeout.toMinutes() > 1 && game.getTurn().equals(game.getP1username())){
                game.setGameWinner(game.getP2username());
                game.setMessage("GAMEOVER " + game.getP2username() + " wins the game!!");
                game.setStatus(Status.FINISHED);
                game.setState(GameState.GAMEOVER);
                User one = userRepo.findByUsername(game.getP1username());
                User two = userRepo.findByUsername(game.getP2username());
                one.setInGame(false);
                two.setInGame(false);
                one.setCurrentGame(null);
                two.setCurrentGame(null);
                userRepo.save(one);
                userRepo.save(two);
                game.setLastMoveTime(LocalDateTime.now());
                gameRepo.save(game);
                goToTable(game, username, request, response);
                return;
            }
            if(timeout.toMinutes() > 1 && game.getTurn().equals(game.getP2username())){
                game.setGameWinner(game.getP1username());
                game.setMessage("GAMEOVER " + game.getP1username() + " wins the game!!");
                game.setStatus(Status.FINISHED);
                game.setState(GameState.GAMEOVER);
                User one = userRepo.findByUsername(game.getP1username());
                User two = userRepo.findByUsername(game.getP2username());
                one.setInGame(false);
                two.setInGame(false);
                one.setCurrentGame(null);
                two.setCurrentGame(null);
                userRepo.save(one);
                userRepo.save(two);
                game.setLastMoveTime(LocalDateTime.now());
                gameRepo.save(game);
                goToTable(game, username, request, response);
                return;
            }
            Deck deck = new Deck(game.getDeck().toArray(new Card[0]));
            goToTable(game,username,request,response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("homepage");
            requestDispatcher.forward(request,response);
        }

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
        UserRepository userRepo = (UserRepository) springContext.getBean("userRepository");

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
            case "START": // form action from the pregame lobby start button
                game.setStatus(Status.ACTIVE);
                game.setState(GameState.BETONE);
                ArrayList<Card> p1Hand = deck.deal(5);
                ArrayList<Card> p2Hand = deck.deal(5);
                game.setP1Hand(p1Hand);
                game.setP2Hand(p2Hand);
                game.setPrevP1Bet(0);
                game.setPrevP2Bet(0);
                game.setLastMoveTime(LocalDateTime.now());
                game.setCurrentPot(0);
                game.setHandTurn(0);
                game.setDeck(deck.getDeck());
                gameRepo.save(game);

                goToTable(game,username,request,response);
                break;
            case "BETONE": //form action from first round bet in table.html
                String s = request.getParameter("betamount");
                if(s.equals("")) {
                    goToTable(game, username, request, response);
                    break;
                }
                int betAmount;
                try{
                    betAmount = Integer.parseInt(s);
                } catch (NumberFormatException e){
                    goToTable(game, username, request, response);
                    break;
                }
                if(username.equals(game.getP1username())) {
                    if(game.getP1balance() - betAmount != 0) {
                        if (betAmount < 0 || betAmount + game.getPrevP1Bet() < game.getPrevP2Bet() || betAmount > game.getP1balance()) {
                            //throw error "Call or Raise"
                            goToTable(game, username, request, response);
                            break;
                        }
                    }
                    game.setCurrentPot(game.getCurrentPot() + betAmount);
                    if(betAmount + game.getPrevP1Bet() == game.getPrevP2Bet() && game.getHandTurn() != 0 || game.getP1balance() == 0){ //Check to see if they are calling or "Checking" both bet 0
                        game.setP1balance(game.getP1balance() - betAmount); //Update p1 balance
                        game.setLastMove(username + " called!");
                        game.setMessage(username + " called!");
                        game.setLastMoveTime(LocalDateTime.now());
                        game.setState(getNextState(game.getState()));
                        game.setTurn(game.getP2username());
                        game.setHandTurn(0);
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
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
                    if(game.getP2balance() - betAmount != 0) {
                        if (betAmount < 0 || betAmount + game.getPrevP2Bet() < game.getPrevP1Bet() || betAmount > game.getP2balance()) {
                            //throw error "Call or Raise"
                            goToTable(game, username, request, response);
                            break;
                        }
                    }
                    game.setCurrentPot(game.getCurrentPot() + betAmount);
                    if(betAmount + game.getPrevP2Bet() == game.getPrevP1Bet() || game.getP2balance() == 0){  //Check to see if they are calling or "Checking" both bet 0
                        game.setP2balance(game.getP2balance() - betAmount); //Update p2 balance
                        game.setLastMove(username + " called!");
                        game.setMessage(username + " called!");
                        game.setLastMoveTime(LocalDateTime.now());
                        game.setState(getNextState(game.getState()));
                        game.setTurn(game.getP1username());
                        game.setPrevP1Bet(0);
                        game.setPrevP2Bet(0);
                        game.setHandTurn(0);
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
                    game.setP2balance(game.getP2balance() - betAmount); //Update p2 balance
                    game.setTurn(game.getP1username()); //Changing turns
                    game.setLastMove(username + " bet $" + betAmount);
                    game.setMessage(username + " bet $" + betAmount);
                    game.setLastMoveTime(LocalDateTime.now());
                    game.setPrevP2Bet(betAmount + game.getPrevP2Bet()); //Previous bet for checking
                    game.setHandTurn(game.getHandTurn() + 1);
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                break;
            case "DRAW": // form action from draw form in table.html
                Collection<Card> temp1 = game.getP1Hand();
                Collection<Card> temp2 = game.getP2Hand();
                ArrayList<Card> player1Hand = new ArrayList<>();
                ArrayList<Card> player2Hand = new ArrayList<>();

                for(Card c : temp1)
                {
                    player1Hand.add(c);
                }
                for(Card c : temp2)
                {
                    player2Hand.add(c);
                }

                if(username.equals(game.getP1username())) {
                    String card0 = request.getParameter("card0");
                    String card1 = request.getParameter("card1");
                    String card2 = request.getParameter("card2");
                    String card3 = request.getParameter("card3");
                    String card4 = request.getParameter("card4");
                    if (card0 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player1Hand.get(0));
                        player1Hand.set(0,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card1 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player1Hand.get(1));
                        player1Hand.set(1,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card2 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player1Hand.get(2));
                        player1Hand.set(2,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card3 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player1Hand.get(3));
                        player1Hand.set(3,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card4 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player1Hand.get(4));
                        player1Hand.set(4,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    game.setP1Hand(player1Hand);
                    game.setHandTurn(game.getHandTurn() + 1);
                    game.setDeck(deck.getDeck());
                    game.setTurn(game.getP2username());
                    if(game.getHandTurn() >= 2){
                        game.setHandTurn(0);
                        game.setState(getNextState(game.getState()));
                        game.setLastMoveTime(LocalDateTime.now());
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
                    game.setLastMoveTime(LocalDateTime.now());
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                if(username.equals(game.getP2username())) {
                    String card0 = request.getParameter("card0");
                    String card1 = request.getParameter("card1");
                    String card2 = request.getParameter("card2");
                    String card3 = request.getParameter("card3");
                    String card4 = request.getParameter("card4");
                    if (card0 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player2Hand.get(0));
                        player2Hand.set(0,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card1 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player2Hand.get(1));
                        player2Hand.set(1,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card2 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player2Hand.get(2));
                        player2Hand.set(2,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card3 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player2Hand.get(3));
                        player2Hand.set(3,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    if (card4 != null) {
                        ArrayList<Card> temp = new ArrayList<>();
                        temp.add(player2Hand.get(4));
                        player2Hand.set(4,deck.deal(1).get(0));
                        deck.returnCards(temp);
                    }
                    game.setP2Hand(player2Hand);
                    game.setHandTurn(game.getHandTurn() + 1);
                    game.setDeck(deck.getDeck());
                    game.setTurn(game.getP1username());
                    if(game.getHandTurn() >= 2){
                        game.setHandTurn(0);
                        game.setState(getNextState(game.getState()));
                        game.setLastMoveTime(LocalDateTime.now());
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
                    game.setLastMoveTime(LocalDateTime.now());
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                break;
            case "BETTWO":// form action from second round bet in table.html
                s = request.getParameter("betamount");
                if(s.equals("")) {
                    goToTable(game, username, request, response);
                    break;
                }
                try{
                    betAmount = Integer.parseInt(s);
                } catch (NumberFormatException e){
                    goToTable(game, username, request, response);
                    break;
                }
                if(username.equals(game.getP1username())) {
                    if(game.getP1balance() - betAmount != 0) {
                        if (betAmount < 0 || betAmount + game.getPrevP1Bet() < game.getPrevP2Bet() || betAmount > game.getP1balance()) {
                            //throw error "Call or Raise"
                            goToTable(game, username, request, response);
                            break;
                        }
                    }
                    game.setCurrentPot(game.getCurrentPot() + betAmount);
                    if(betAmount + game.getPrevP1Bet() == game.getPrevP2Bet() && game.getHandTurn() != 0
                            || game.getP1balance() == 0){ //Check to see if they are calling or "Checking" both bet 0
                        game.setP1balance(game.getP1balance() - betAmount); //Update p1 balance
                        game.setLastMove(username + " called!");
                        game.setMessage(username + " called!");
                        game.setLastMoveTime(LocalDateTime.now());
                        game.setState(getNextState(game.getState()));
                        game.setTurn(game.getP2username());
                        game.setHandTurn(0);
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
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
                    if(game.getP2balance() - betAmount != 0) {
                        if (betAmount < 0 || betAmount + game.getPrevP2Bet() < game.getPrevP1Bet() || betAmount > game.getP2balance()) {
                            //throw error "Call or Raise"
                            goToTable(game, username, request, response);
                            break;
                        }
                    }
                    game.setCurrentPot(game.getCurrentPot() + betAmount);
                    if(betAmount + game.getPrevP2Bet() == game.getPrevP1Bet() || game.getP2balance() == 0){  //Check to see if they are calling or "Checking" both bet 0
                        game.setP2balance(game.getP2balance() - betAmount); //Update p2 balance
                        game.setLastMove(username + " called!");
                        game.setMessage(username + " called!");
                        game.setLastMoveTime(LocalDateTime.now());
                        game.setState(getNextState(game.getState()));
                        game.setTurn(game.getP1username());
                        game.setHandTurn(0);
                        gameRepo.save(game);
                        goToTable(game, username, request, response);
                        break;
                    }
                    game.setP2balance(game.getP2balance() - betAmount); //Update p2 balance
                    game.setTurn(game.getP1username()); //Changing turns
                    game.setLastMove(username + " bet $" + betAmount);
                    game.setMessage(username + " bet $" + betAmount);
                    game.setLastMoveTime(LocalDateTime.now());
                    game.setPrevP2Bet(betAmount + game.getPrevP2Bet()); //Previous bet for checking
                    game.setHandTurn(game.getHandTurn() + 1);
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                break;
            case "SHOWHAND":// form action, we have a form thats just an ok button for both players after both hands are shown on table.html
                Card[] cardP1 = new Card [5];
                Card[] cardP2 = new Card [5];
                cardP1 = game.getP1Hand().toArray(cardP1);
                cardP2 = game.getP2Hand().toArray(cardP2);
                String result = compareHands(cardP1, cardP2);
                String winningHand;
                if(result.equals("Player1")){
                    game.setP1balance(game.getP1balance() + game.getCurrentPot());
                    winningHand = determineHand(cardP1).getName();
                    game.setMessage(game.getP1username() + " has won the hand with a " + winningHand + "!");
                } else if (result.equals("Player2")) {
                    game.setP2balance(game.getP2balance() + game.getCurrentPot());
                    winningHand = determineHand(cardP2).getName();
                    game.setMessage(game.getP2username() + " has won the hand with a " + winningHand + "!");
                } else {
                    game.setP1balance(game.getP1balance() + (game.getCurrentPot()/2));
                    game.setP2balance(game.getP2balance() + (game.getCurrentPot()/2));
                    game.setMessage("Hand ended in a draw...");
                }
                if(game.getP1balance() == 0){
                    game.setGameWinner(game.getP2username());
                    game.setMessage("GAMEOVER " + game.getP2username() + " wins the game!!");
                    game.setStatus(Status.FINISHED);
                    game.setState(GameState.GAMEOVER);
                    User one = userRepo.findByUsername(game.getP1username());
                    User two = userRepo.findByUsername(game.getP2username());
                    one.setInGame(false);
                    two.setInGame(false);
                    one.setCurrentGame(null);
                    two.setCurrentGame(null);
                    userRepo.save(one);
                    userRepo.save(two);
                    game.setLastMoveTime(LocalDateTime.now());
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }
                if(game.getP2balance() == 0){
                    game.setGameWinner(game.getP1username());
                    game.setMessage("GAMEOVER " + game.getP1username() + " wins the game!!");
                    game.setStatus(Status.FINISHED);
                    game.setState(GameState.GAMEOVER);
                    User one = userRepo.findByUsername(game.getP1username());
                    User two = userRepo.findByUsername(game.getP2username());
                    one.setInGame(false);
                    two.setInGame(false);
                    one.setCurrentGame(null);
                    two.setCurrentGame(null);
                    userRepo.save(one);
                    userRepo.save(two);
                    game.setLastMoveTime(LocalDateTime.now());
                    gameRepo.save(game);
                    goToTable(game, username, request, response);
                }

                p1Hand = deck.deal(5);
                p2Hand = deck.deal(5);

                ArrayList<Card> cardsToReturn = new ArrayList<>();

                for(Card c : cardP1)
                {
                    cardsToReturn.add(c);
                }
                for(Card c : cardP2)
                {
                    cardsToReturn.add(c);
                }
                deck.returnCards(cardsToReturn);

                game.setP1Hand(p1Hand);
                game.setP2Hand(p2Hand);
                game.setPrevP1Bet(0);
                game.setPrevP2Bet(0);
                game.setCurrentPot(0);
                game.setHandTurn(0);
                game.setDeck(deck.getDeck());
                game.setState(GameState.BETONE);
                game.setLastMoveTime(LocalDateTime.now());
                gameRepo.save(game);
                goToTable(game, username, request, response);
                break;
            case "GAMEOVER": // form acton, ok button to end game, cleanup game and exit to homepage
                break;
            case "FOLD":
                cardP1 = new Card [5];
                cardP2 = new Card [5];
                cardP1 = game.getP1Hand().toArray(cardP1);
                cardP2 = game.getP2Hand().toArray(cardP2);
                if(username.equals(game.getP1username())){
                    game.setP2balance(game.getP2balance() + game.getCurrentPot());
                    game.setMessage(username + " folded the hand. " + game.getP2username() + " wins!");
                    game.setTurn(game.getP2username());
                } else if(username.equals(game.getP2username())){
                    game.setP1balance(game.getP2balance() + game.getCurrentPot());
                    game.setMessage(username + " folded the hand. " + game.getP1username() + " wins!");
                    game.setTurn(game.getP1username());
                }
                p1Hand = deck.deal(5);
                p2Hand = deck.deal(5);

                cardsToReturn = new ArrayList<>();

                for(Card c : cardP1)
                {
                    cardsToReturn.add(c);
                }
                for(Card c : cardP2)
                {
                    cardsToReturn.add(c);
                }
                deck.returnCards(cardsToReturn);

                game.setP1Hand(p1Hand);
                game.setP2Hand(p2Hand);
                game.setPrevP1Bet(0);
                game.setPrevP2Bet(0);
                game.setCurrentPot(0);
                game.setHandTurn(0);
                game.setDeck(deck.getDeck());
                game.setState(GameState.BETONE);
                game.setLastMoveTime(LocalDateTime.now());
                gameRepo.save(game);
                goToTable(game, username, request, response);
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
            gb.setOpponentHand(game.getP2Hand().toArray(new Card[0]));  //them
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
        session.setAttribute("username", username);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("table");
        requestDispatcher.forward(request,response);
    }

}
