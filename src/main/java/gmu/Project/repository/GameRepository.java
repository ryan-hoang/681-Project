package gmu.Project.repository;

import gmu.Project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface GameRepository extends JpaRepository<Game,Long>
{

    Game findByGameId(Long id);

    @Query(value = "SELECT * FROM game g WHERE g.status = 'ACTIVE'", nativeQuery = true)
    Collection<Game> getActiveGames();

    @Query(value = "SELECT * FROM game g WHERE g.status = 'PREGAME'", nativeQuery = true)
    Collection<Game> getPendingGames();

    @Query(value = "SELECT g FROM game g WHERE g.status = 'ACTIVE' and (g.p1username = ?1 || g.p2username = ?1)", nativeQuery = true)
    Collection<Game> getMyActiveGame(String user);
}
