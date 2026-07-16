package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unibook.app.model.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByTitle(String title);

    Page<Publisher> findByDeletedAtIsNull(Pageable pageable);
    @Query("""
        SELECT p
        FROM Publisher p
        WHERE p.deletedAt IS NULL
        AND (
            LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Publisher> searchActivePublishers(@Param("search") String search,Pageable pageable);

}
