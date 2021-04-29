package gmu.Project.Services;

import gmu.Project.model.Authority;
import gmu.Project.model.GameMove;
import gmu.Project.model.User;
import gmu.Project.repository.LogRepository;
import gmu.Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public GameMove save (GameMove gm)
    {
        return logRepository.save(gm);
    }

    public List<GameMove> findByGameIdOrderByTimestamp(Long id){
        return logRepository.findByGameIdOrderByTimestamp(id);
    }
}
