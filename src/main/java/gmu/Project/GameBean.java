package gmu.Project;

import java.util.ArrayList;

public class GameBean implements java.io.Serializable
{
    private ArrayList<Card> hand = null;
    private double money = 0.0;

    public GameBean(){}

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
