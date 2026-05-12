package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unibook.app.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a JOIN a.person p WHERE p.name = :name")
    Optional<Author> findByPersonName(String name);

}
