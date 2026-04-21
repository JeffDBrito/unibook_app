package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByTitle(String title);
    
}
