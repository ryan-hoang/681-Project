package gmu.Project;

public class GameBean implements java.io.Serializable
{
    private String handWinner = "";//
    private Hand winningHand = null;//
    private Card[] opponentHand = null;//
    private Card[] hand = null;//
    private int myMoney = 0;//
    private int opponentMoney = 0;//
    private String user = "";//
    private String turn = "";//
    private GameState gs = null;//
    private String gameWinner = "";//
    private String opponent = "";//

    public GameBean(){}

    public String getHandWinner() {
        return handWinner;
    }
    public void setHandWinner(String handWinner) {
        this.handWinner = handWinner;
    }

    public Hand getWinningHand() {
        return winningHand;
    }
    public void setWinningHand(Hand winningHand) {
        this.winningHand = winningHand;
    }

    public Card[] getOpponentHand()
    {
        return opponentHand;
    }
    public void setOpponentHand(Card[] hand) {
        this.opponentHand = hand;
    }

    public Card[] getHand()
    {
        return hand;
    }
    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public int getMyMoney() {
        return myMoney;
    }
    public void setMyMoney(int money) {
        this.myMoney = money;
    }

    public int getOpponentMoney() {
        return opponentMoney;
    }
    public void setOpponentMoney(int opponentMoney) {
        this.opponentMoney = opponentMoney;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getUserTurn() {
        return turn;
    }
    public void setUserTurn(String turn) {
        this.turn = turn;
    }

    public GameState getGs() {
        return gs;
    }
    public void setGs(GameState gs) {
        this.gs = gs;
    }

    public String getGameWinner() {
        return gameWinner;
    }
    public void setGameWinner(String gameWinner) {
        this.gameWinner = gameWinner;
    }

    public String getOpponent() {
        return opponent;
    }
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
