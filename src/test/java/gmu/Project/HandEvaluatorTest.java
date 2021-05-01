package gmu.Project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static gmu.Project.HandComparison.compareHands;
import static gmu.Project.HandComparison.determineHand;
import static gmu.Project.HandEvaluator.*;
import static org.junit.jupiter.api.Assertions.*;


class HandEvaluatorTest {

    @Test
    public void cardTester(){
        Card[] hand = new Card[5];
        Card a = new Card(Card.CardValue.JACK, Card.Suit.CLUBS);
        Card b = new Card(Card.CardValue.JACK, Card.Suit.CLUBS);
        Card c = new Card(Card.CardValue.QUEEN, Card.Suit.DIAMONDS);
        Card d = new Card(Card.CardValue.EIGHT, Card.Suit.CLUBS);
        Card e = new Card(Card.CardValue.TEN, Card.Suit.CLUBS);
        hand[0] = a;
        hand[1] = b;
        hand[2] = c;
        hand[3] = d;
        hand[4] = e;
        Card[] hand2 = new Card[5];
        Card f = new Card(Card.CardValue.QUEEN, Card.Suit.DIAMONDS);
        Card g = new Card(Card.CardValue.QUEEN, Card.Suit.HEARTS);
        Card h = new Card(Card.CardValue.QUEEN, Card.Suit.HEARTS);
        Card i = new Card(Card.CardValue.NINE, Card.Suit.HEARTS);
        Card j = new Card(Card.CardValue.TEN, Card.Suit.HEARTS);
        hand2[0] = f;
        hand2[1] = g;
        hand2[2] = h;
        hand2[3] = i;
        hand2[4] = j;


        return;
    }
    @Test
    public void deckSizeTest()
    {
        Deck deck = new Deck();
        assertEquals(deck.getDeck().size(),52);
    }

    @Test
    public void deckTest()
    {
        Deck deck = new Deck();
        ArrayList<Card> hand1 = deck.deal(15);
        ArrayList<Card> hand2 = deck.deal(15);

        HashSet<Card> set = new HashSet<>()
        for(int i = 0; i<100; i++)
        {
            for(Card c : hand1)
            {
                assert(set.add(c);
            }
        }
    }





}