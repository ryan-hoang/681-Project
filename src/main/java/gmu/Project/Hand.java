package gmu.Project;

import java.io.Serializable;

public class Hand implements Serializable
{
    private Card[] cards = null;

    public Hand(Card[] h)
    {
        this.cards = h;
    }
    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
