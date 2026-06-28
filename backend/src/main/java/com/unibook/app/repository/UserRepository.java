package com.unibook.app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unibook.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // findAll()
    // findById()
    // save()
    // delete()

    Optional<User> findByLogin(String login);
    
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.login = :login")
    Optional<User> findByLoginWithRoles(String login);
    Boolean existsByPersonEmail(String email);
    Boolean existsByLogin(String email);

}