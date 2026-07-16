package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unibook.app.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitle(String title);
    Boolean existsByIsbn(String isbn);
    @Query("""
        SELECT b
        FROM Book b
        WHERE b.deletedAt IS NULL
        AND (
            LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Book> searchActiveBooks(
        @Param("search") String search,
        Pageable pageable
    );

    Page<Book> findByDeletedAtIsNull(Pageable pageable);

}
