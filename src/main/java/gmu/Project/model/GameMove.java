package gmu.Project.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GameMove")
public class GameMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moveId;
    private Long gameId;
    private String username;
    @Enumerated
    private Move move;
    private Integer betAmount;
    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getMoveId() {
        return moveId;
    }
    public void setMoveId(Long moveId) {
        this.moveId = moveId;
    }

    public Long getGameId() {
        return gameId;
    }
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Move getMove() {
        return move;
    }
    public void setMove(Move move) {
        this.move = move;
    }

    public Integer getBetAmount() {
        return betAmount;
    }
    public void setBetAmount(Integer betAmount) {
        this.betAmount = betAmount;
    }
}
