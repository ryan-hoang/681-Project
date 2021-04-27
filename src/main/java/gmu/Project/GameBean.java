package gmu.Project;

import java.util.ArrayList;

public class GameBean implements java.io.Serializable
{
    private Card[] hand = null;
    private double money = 0.0;
    private Turn turn = null;
    private GameState gs = null;

    public GameBean(){}

    public Turn getTurn() {
        return turn;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
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

    public GameState getGs() {
        return gs;
    }
    public void setGs(GameState gs) {
        this.gs = gs;
    }
}
