package gmu.Project;

public class GameBean implements java.io.Serializable
{
    private String winner = "";
    private Hand winningHand = null;
    private Card[] opponentHand = null;
    private Card[] hand = null;
    private double money = 0.0;
    private String user = "";
    private String turn = "";
    private GameState gs = null;

    public GameBean(){}

    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
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

    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
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
}
