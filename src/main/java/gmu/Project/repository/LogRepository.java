package gmu.Project.repository;

import gmu.Project.model.GameMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogRepository extends JpaRepository<GameMove, Long>
{
    List<GameMove> findByGameIdOrderByTimestamp(Long GameId);
}
