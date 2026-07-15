package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    Page<User> findByDeletedAtIsNull(Pageable pageable);
    Optional<User> findByIdAndDeletedAtIsNull(Long id);
    Optional<User> findByLoginAndDeletedAtIsNull(String login); 

    @Query("""
        SELECT u
        FROM User u
        JOIN u.person p
        WHERE u.deletedAt IS NULL
          AND (
            LOWER(u.login) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
          )
    """)
    Page<User> searchActiveUsers(@Param("search") String search,Pageable pageable);

}