package gmu.Project.model;


import gmu.Project.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;
    private String p1username = "";
    private String p2username = "";
    private Integer p1balance;
    private Integer p2balance;
    @Enumerated
    private GameState state;
    private String turn;
    private LocalDateTime lastMoveTime;
    private String gameWinner;
    private String handWinner;
    @Enumerated
    private Hand winningHand;
    @ElementCollection
    private Collection<Card> p2Hand;
    @ElementCollection
    private Collection<Card> p1Hand;
    @Enumerated
    private Status status;
    private String gameOwner;
    private int currentPot;
    @ElementCollection
    private Collection<Card> deck;
    private String lastMove;
    private int prevBet;
    private String message;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastMove() {
        return lastMove;
    }
    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public Collection<Card> getDeck() {
        return deck;
    }
    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public int getCurrentPot() {
        return currentPot;
    }
    public void setCurrentPot(int currentPot) {
        this.currentPot = currentPot;
    }

    public String getGameOwner() {
        return gameOwner;
    }
    public void setGameOwner(String gameOwner) {
        this.gameOwner = gameOwner;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getHandWinner() {
        return handWinner;
    }
    public void setHandWinner(String handWinner) {
        this.handWinner = handWinner;
    }

    public Hand getWinningHand() {
        return winningHand;
    }
    public void setWinningHand(Hand winningHand) {
        this.winningHand = winningHand;
    }

    public Collection<Card> getP2Hand() {
        return p2Hand;
    }
    public void setP2Hand(ArrayList<Card> p2Hand) {
        this.p2Hand = p2Hand;
    }

    public Collection<Card> getP1Hand() {
        return p1Hand;
    }
    public void setP1Hand(ArrayList<Card> p1Hand) {
        this.p1Hand = p1Hand;
    }

    public Long getGameId() {
        return gameId;
    }
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getP1username() {
        return p1username;
    }
    public void setP1username(String p1username) {
        this.p1username = p1username;
    }

    public String getP2username() {
        return p2username;
    }
    public void setP2username(String p2username) {
        this.p2username = p2username;
    }

    public Integer getP1balance() {
        return p1balance;
    }
    public void setP1balance(Integer p1balance) {
        this.p1balance = p1balance;
    }

    public Integer getP2balance() {
        return p2balance;
    }
    public void setP2balance(Integer p2balance) {
        this.p2balance = p2balance;
    }

    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public String getTurn() {
        return turn;
    }
    public void setTurn(String turn) {
        this.turn = turn;
    }

    public LocalDateTime getLastMoveTime() {
        return lastMoveTime;
    }
    public void setLastMoveTime(LocalDateTime lastMove) {
        this.lastMoveTime = lastMove;
    }

    public String getGameWinner() {
        return gameWinner;
    }
    public void setGameWinner(String winner) {
        this.gameWinner = gameWinner;
    }

    public int getPrevBet() { return prevBet; }
    public void setPrevBet(int prevBet) { this.prevBet = prevBet; }
}
