package gmu.Project.repository;

import gmu.Project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import gmu.Project.Status;

import java.util.Collection;

public interface GameRepository extends JpaRepository<Game,Long>
{

    Game findByGameId(Long id);

    @Query(value = "SELECT * FROM game g WHERE g.status = gmu.Project.Status.ACTIVE", nativeQuery = true)
    Collection<Game> getActiveGames();

    @Query(value = "SELECT * FROM game g WHERE g.status = gmu.Project.Status.PREGAME", nativeQuery = true)
    Collection<Game> getPendingGames();

    @Query(value = "SELECT g FROM game g WHERE g.status = gmu.Project.Status.ACTIVE and (g.p1username = ?1 || g.p2username = ?1)", nativeQuery = true)
    Collection<Game> getMyActiveGame(String user);
}
