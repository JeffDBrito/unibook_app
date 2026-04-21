package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Category;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findByTitle(String title);
    
}
