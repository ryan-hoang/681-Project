package gmu.Project.model;


import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;
    private String p1username;
    private String p2username;
    private String p1time;
    private String p2time;
    private Integer p1balance;
    private Integer p2balance;
    @Enumerated
    private GameStatus status;
    private String turn;
    private String lastMove;
    private String winner;

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

    public String getP1time() {
        return p1time;
    }

    public void setP1time(String p1time) {
        this.p1time = p1time;
    }

    public String getP2time() {
        return p2time;
    }

    public void setP2time(String p2time) {
        this.p2time = p2time;
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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
