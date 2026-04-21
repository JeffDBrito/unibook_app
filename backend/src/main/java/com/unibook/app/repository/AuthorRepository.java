package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    Optional<Author> findByName(String name);

}
