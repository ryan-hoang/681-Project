package gmu.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmu.Project.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET current_game=null, in_game=false", nativeQuery = true)
    void clearGames();
}
