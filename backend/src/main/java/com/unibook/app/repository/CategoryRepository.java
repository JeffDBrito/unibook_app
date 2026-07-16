package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unibook.app.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findByTitle(String title);
    Page<Category> findByDeletedAtIsNull(Pageable pageable);

    @Query("""
        SELECT c
        FROM Category c
        WHERE c.deletedAt IS NULL
        AND (
            LOWER(c.title)
                LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(COALESCE(c.description, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Category> searchActiveCategories(@Param("search") String search,Pageable pageable);
}
