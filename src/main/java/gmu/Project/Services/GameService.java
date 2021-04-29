package gmu.Project.Services;

import gmu.Project.model.Game;
import gmu.Project.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GameService
{
    @Autowired
    private GameRepository gameRepository;

    public Game save(Game game)
    {
        return gameRepository.save(game);
    }

    public Collection<Game> getPendingGames()
    {
        return gameRepository.getPendingGames();
    }

    public Game getMyActiveGame(String user)
    {
        return gameRepository.getMyActiveGame(user);
    }

    public Collection<Game> getActiveGames()
    {
        return gameRepository.getActiveGames();
    }

    public Game findByGameId(Long id)
    {
        return gameRepository.findByGameId(id);
    }

    public boolean hasPendingGame(String user) { return gameRepository.hasPendingGame(user) == 1 ? true : false; }
}
