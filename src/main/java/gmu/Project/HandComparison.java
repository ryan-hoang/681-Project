package gmu.Project;

import static gmu.Project.Hand.HIGHCARD;
import static gmu.Project.HandEvaluator.*;

public class HandComparison {

    public static String compareHands(Card[] player1Hand, Card[] player2Hand) {
        int playerOne = determineHand(player1Hand).getHandInt();
        int playerTwo = determineHand(player2Hand).getHandInt();
        Hand hand1 = determineHand(player1Hand);
        Hand hand2 = determineHand(player2Hand);
        if(playerOne > playerTwo){
            return "Player1";
        }
        if(playerOne < playerTwo){
            return "Player2";
        }
        else {
            int handType = playerOne;
            String result = equalHandComparer(player1Hand, player2Hand, handType);
            return result;
        }
    }

    public static String equalHandComparer(Card[] p1, Card[] p2, int handType)
    {
        p1 = sortByRank(p1);
        p2 = sortByRank(p2);
        int temp1 = 0;
        int temp11 = 0;
        int temp2 = 0;
        int temp22 = 0;
        //Royal Flush
        if(handType == 10)
        {
            return "Draw";
        }
        //Straight && Straight Flush
        if(handType == 5 || handType == 9)
        {
            if(p1[4].cardValue.getCardValue() == p2[4].cardValue.getCardValue()) {
                return "Draw";
            }
            if(p1[4].cardValue.getCardValue() > p2[4].cardValue.getCardValue()) {
                return "Player1";
            }
            else{
                return "Player2";
            }
        }
        //Four of A Kind
        else if(handType == 8){
            if(p1[4] == p1[3]){
                temp1 = p1[4].cardValue.getCardValue();
            }
            else if(p1[0] == p1[1]){
                temp1 = p1[0].cardValue.getCardValue();
            }
            if(p2[4] == p2[3]){
                temp2 = p1[4].cardValue.getCardValue();
            }
            else if(p2[0] == p2[1]){
                temp2 = p1[0].cardValue.getCardValue();
            }
            if(temp1 > temp2) {
                return "Player1";
            }
            else{
                return "Player2";
            }
        }
        //Full House or Three-of-a-Kind
        else if(handType == 7 || handType == 3){
            if(p1[3].cardValue.getCardValue() > p2[3].cardValue.getCardValue()){
                return "Player1";
            }
            else{
                return "Player2";
            }
        }
        //Flush or High Card
        else if(handType == 6 || handType == 1){
            return highestCardWin(p1, p2);
        }
        //Two-Pair
        else if(handType == 3) {
            if (p1[4].cardValue.getCardValue() == p1[3].cardValue.getCardValue()) {
                temp1 = p1[4].cardValue.getCardValue();
            } else {
                temp1 = p1[3].cardValue.getCardValue();
            }
            if (p2[4].cardValue.getCardValue() == p2[3].cardValue.getCardValue()) {
                temp2 = p2[4].cardValue.getCardValue();
            } else {
                temp2 = p2[3].cardValue.getCardValue();
            }
            temp11 = p1[1].cardValue.getCardValue();
            temp22 = p2[1].cardValue.getCardValue();
            if(temp1 > temp2){
                return "Player1";
            }
            else if (temp1 < temp2){
                return "Player2";
            } else {
                if(temp11 > temp22){
                    return "Player1";
                }
                else if(temp11 < temp22){
                    return "Player2";
                } else {
                    return highestCardWin(p1, p2);
                }
            }
        }
        //Pair
        if(handType == 2){
            if(p1[0].cardValue.getCardValue() == p1[1].cardValue.getCardValue() || p1[1].cardValue.getCardValue() == p1[2].cardValue.getCardValue()){
                temp1 = p1[1].cardValue.getCardValue();
            } else {
                temp1 = p1[3].cardValue.getCardValue();
            }
            if(p2[0].cardValue.getCardValue() == p2[1].cardValue.getCardValue() || p2[1].cardValue.getCardValue() == p2[2].cardValue.getCardValue()){
                temp2 = p2[1].cardValue.getCardValue();
            } else {
                temp2 = p2[3].cardValue.getCardValue();
            }
            if(temp1 > temp2){
                return "Player1";
            }
            else if (temp1 < temp2){
                return "Player2";
            } else {
                return highestCardWin(p1, p2);
            }
        }
        return "What are these hands?";
    }

    public static Hand determineHand(Card[] playerHand)
    {
        Hand result = HIGHCARD;
        if(isRoyalFlush(playerHand)){
            return result.ROYALFLUSH;
        }
        if(isStraightFlush(playerHand)){
            return result.STRAIGHTFLUSH;
        }
        if(is4ofaKind(playerHand)){
            return result.FOUROFAKIND;
        }
        if(isFullHouse(playerHand)){
            return result.FULLHOUSE;
        }
        if(isFlush(playerHand)){
            return result.FLUSH;
        }
        if(isStraight(playerHand)){
            return result.STRAIGHT;
        }
        if(is3ofaKind(playerHand)){
            return result.THREEOFAKIND;
        }
        if(isTwoPair(playerHand)){
            return result.TWOPAIR;
        }
        if(isPair(playerHand)){
            return result.ONEPAIR;
        }
        else
            return result.HIGHCARD;
    }

    public static String highestCardWin(Card[] p1, Card[] p2){
        int i = 4;
        while(p1[i].cardValue.getCardValue() == p2[i].cardValue.getCardValue() && i != 0){
            i--;
        }
        if(p1[i].cardValue.getCardValue() == p2[i].cardValue.getCardValue()){
            return "Draw";
        }
        if(p1[i].cardValue.getCardValue() > p2[i].cardValue.getCardValue()){
            return "Player1";
        } else {
            return "Player2";
        }
    }
}
