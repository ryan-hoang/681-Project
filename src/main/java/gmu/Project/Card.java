package gmu.Project;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable
{
    private static final long serialVersionUID = 6529685098267757690L;
    public enum CardValue
    {
        ACE(14,"A"),
        TWO(2,"2"),
        THREE(3,"3"),
        FOUR(4,"4"),
        FIVE(5,"5"),
        SIX(6,"6"),
        SEVEN(7,"7"),
        EIGHT(8,"8"),
        NINE(9,"9"),
        TEN(10,"10"),
        JACK(11,"J"),
        QUEEN(12,"Q"),
        KING(13,"K");

        private int cardValue;
        private final String filename;
        CardValue(int value, String filename) {
            this.cardValue = value;
            this.filename = filename;
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

    public final Suit suit;
    public final CardValue cardValue;

    public Card(CardValue cardValue, Suit suit)
    {
        this.cardValue = cardValue;
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && cardValue == card.cardValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, cardValue);
    }

    public String toString ()
    {
        return cardValue.name() + " of " + suit.name();
    }

    public String lookupFile()
    {
        return cardValue.filename + suit.value + ".png";
    }

}
