package gmu.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmu.Project.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);
}
