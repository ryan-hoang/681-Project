package gmu.Project.repository;

import gmu.Project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;

public interface GameRepository extends JpaRepository<Game,Long>
{

    Game findByGameId(Long id);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE game", nativeQuery = true)
    void truncateGame();

    @Query(value = "SELECT * FROM game g WHERE g.status = 0", nativeQuery = true)
    Collection<Game> getActiveGames();

    @Query(value = "SELECT * FROM game g WHERE g.status = 2", nativeQuery = true)
    Collection<Game> getPendingGames();

    @Query(value = "SELECT * FROM game g WHERE g.status = 0 and (g.p1username = ?1 || g.p2username = ?1)", nativeQuery = true)
    Game getMyActiveGame(String user);

    @Query(value = "SELECT * FROM game g WHERE g.status = 2 and (g.p1username = ?1 || g.p2username = ?1)", nativeQuery = true)
    Game getMyPendingGame(String user);

    @Query(value = "SELECT EXISTS(SELECT * FROM game g WHERE (g.game_owner = ?1 and g.status = 2))", nativeQuery = true)
    int hasPendingGame(String user);
}
