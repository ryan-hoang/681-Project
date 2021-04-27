package gmu.Project;

import gmu.Project.model.User;

import java.util.ArrayList;

public class GameBean implements java.io.Serializable
{
    private Card[] hand = null;
    private double money = 0.0;
    private User user = null;
    private GameState gs = null;

    public GameBean(){}

    public User getTurn() {
        return user;
    }
    public void setTurn(User user) {
        this.user = user;
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
