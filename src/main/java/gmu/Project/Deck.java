package gmu.Project;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Deck
{
    private SecureRandom rand = new SecureRandom();;
    private ArrayList<Card> deck = new ArrayList<Card>();
    public Deck()
    {

        Card.CardValue[] cv = new Card.CardValue[]{
                    Card.CardValue.ACE,
                    Card.CardValue.TWO,
                    Card.CardValue.THREE,
                    Card.CardValue.FOUR,
                    Card.CardValue.FIVE,
                    Card.CardValue.SIX,
                    Card.CardValue.SEVEN,
                    Card.CardValue.EIGHT,
                    Card.CardValue.NINE,
                    Card.CardValue.TEN,
                    Card.CardValue.JACK,
                    Card.CardValue.QUEEN,
                    Card.CardValue.KING,
                };

        Card.Suit[] suits = new Card.Suit[]{
                Card.Suit.CLUBS,
                Card.Suit.DIAMONDS,
                Card.Suit.HEARTS,
                Card.Suit.SPADES,
        };

        for(Card.CardValue value : cv)
        {
            for (Card.Suit suit : suits)
            {
                deck.add(new Card(value,suit));
            }
        }
    }

    public Deck(ArrayList<Card> deck)
    {
        this.deck = deck;
    }

    public Deck(Card[] deck)
    {
        for (Card c : deck)
        {
            this.deck.add(c);
        }
    }

    public ArrayList<Card> getDeck()
    {
        return deck;
    }

    public void returnCards(ArrayList<Card> cards)
    {
        deck.addAll(cards);
    }

    public ArrayList<Card> deal(int howMany)
    {
        ArrayList<Card> cardsDealt = new ArrayList<Card>();
        for(int i = 0; i < howMany; i++)
        {
            cardsDealt.add(deck.remove( rand.nextInt( deck.size())));
        }
        return cardsDealt;
    }
}
