package gmu.Project;

import java.io.Serializable;

public class Card implements Serializable
{
    public enum Value
    {
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING
    }

    public enum Suit
    {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }

    public final Value value;
    public final Suit suit;

    public Card(Value value, Suit suit)
    {
        this.value = value;
        this.suit = suit;
    }

    public String toString ()
    {
        return value + " of " + suit;
    }

}
