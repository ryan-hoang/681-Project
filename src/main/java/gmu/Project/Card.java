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

    public enum CardValue
    {
        ACE(14),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13);

        private int cardValue;
        CardValue(int value) {
            this.cardValue = value;
        }
        public int getCardValue(){
            return cardValue;
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
        public char getSuit(){
            char[] val = value.toCharArray();
            return val[0]; //returns single character for sorting
        }
    }

    public final Value value;
    public final Suit suit;
    public final CardValue cardValue;

    public Card(Value value, CardValue cardValue, Suit suit)
    {
        this.value = value;
        this.cardValue = cardValue;
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
