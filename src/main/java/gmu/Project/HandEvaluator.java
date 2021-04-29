package gmu.Project;

public class HandEvaluator {


    public static Card[] sortByRank(Card[] hand){
        for(int i = 0; i < 4; i++){
            for(int j = i + 1; j < 5; j++) {
                if (hand[i].cardValue.getCardValue() > hand[j].cardValue.getCardValue()) {
                    Card temp = hand[i];
                    hand[i] = hand[j];
                    hand[j] = temp;
                }
            }
        }
        return hand;
    }

    public static Card[] sortBySuit(Card[] hand){
        for(int i = 0; i < 4; i++){
            for(int j = i + 1; j < 5; j++) {
                if (hand[i].suit.getSuit() > hand[j].suit.getSuit()) {
                    Card temp = hand[i];
                    hand[i] = hand[j];
                    hand[j] = temp;
                }
            }
        }
        return hand;
    }


    public static boolean isFlush(Card[] hand){
        hand = sortBySuit(hand);
        if(hand[0].suit.getSuit() == hand[4].suit.getSuit())
        {
            return true;
        }
        return false;
    }

    public static boolean isStraight(Card[] hand){
        hand = sortByRank(hand);
        if(hand[4].cardValue.getCardValue() == 14){
            boolean x = hand[0].cardValue.getCardValue() == 2 && hand[1].cardValue.getCardValue() == 3 && hand[2].cardValue.getCardValue() == 4 && hand[3].cardValue.getCardValue() == 5;
            boolean y = hand[0].cardValue.getCardValue() == 10 && hand[1].cardValue.getCardValue() == 11 && hand[2].cardValue.getCardValue() == 12 && hand[3].cardValue.getCardValue() == 13;
            return (x || y);
        }
        else {
            int nextCard = hand[0].cardValue.getCardValue() + 1;
            for(int i = 1; i < 5; i++)
            {
                if(hand[1].cardValue.getCardValue() != nextCard){
                    return false;
                }
                nextCard++;
            }
        }
        return true;
    }

    public static boolean isStraightFlush(Card[] hand){
        if(isStraight(hand) && isFlush(hand)){
            return true;
        }
        return false;
    }

    public static boolean isRoyalFlush(Card[] hand){
        if(isStraightFlush(hand) && hand[3].cardValue.getCardValue() == 13){
            return true;
        }
        return false;
    }

    public static boolean is4ofaKind(Card[] hand){
        hand = sortByRank(hand);
        boolean x = hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue() && hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue() && hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue();
        boolean y = hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue() && hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue() && hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();
        return(x || y);
    }

    public static boolean isFullHouse(Card[] hand){
        hand = sortByRank(hand);
        boolean x = hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue() && hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue() && hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();
        boolean y = hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue() && hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue() && hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();
        return (x || y);
    }

    public static boolean is3ofaKind(Card[] hand){
        hand = sortByRank(hand);
        boolean x, y, z;
        x =  hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue() && hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue();
        y =  hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue() && hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue();
        z =  hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue() && hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();

        return(x || y || z);
    }

    public static boolean isTwoPair(Card[] hand){
        hand = sortByRank(hand);
        boolean x, y, z;
        x =  hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue() && hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue();
        y =  hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue() && hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();
        z =  hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue() && hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();

        return(x || y || z);
    }

    public static boolean isPair(Card[] hand){
        hand = sortByRank(hand);
        boolean x, y, z, v;
        x =  hand[0].cardValue.getCardValue() == hand[1].cardValue.getCardValue();
        y =  hand[1].cardValue.getCardValue() == hand[2].cardValue.getCardValue();
        z =  hand[2].cardValue.getCardValue() == hand[3].cardValue.getCardValue();
        v =  hand[3].cardValue.getCardValue() == hand[4].cardValue.getCardValue();

        return(x || y || z || v);
    }

    public static Card getHighCard(Card[] hand){
        hand = sortByRank(hand);
        return hand[4];
    }


}
