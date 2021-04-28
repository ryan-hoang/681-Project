package gmu.Project;

import java.io.Serializable;

public class Card implements Serializable
{
    public enum Value
    {
        ACE("A"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("J"),
        QUEEN("Q"),
        KING("K");

        private final String filename;
        private Value(String filename)
        {
            this.filename = filename;
        }
    }

    public enum Suit
    {
        CLUBS("C"),
        DIAMONDS("D"),
        HEARTS("H"),
        SPADES("S");

        private final String value;
        private Suit (String value)
        {
            this.value = value;
        }
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

    public String lookupFile()
    {
        return value.filename + suit.value + ".png";
    }

}
