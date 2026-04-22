package com.unibook.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
