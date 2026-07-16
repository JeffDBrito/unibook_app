package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unibook.app.enums.CopyStatus;
import com.unibook.app.model.Copy;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface CopyRepository extends JpaRepository<Copy, Long>{
    
    Optional<Copy> findByCode(String code);
    Boolean existsByCode(String code);
    Boolean existsByBookIdAndDeletedAtIsNull(Long bookId);
    Optional<Copy> findFirstByBookIdAndStatusAndDeletedAtIsNull(
        Long bookId,
        CopyStatus status
    );

    @Query("""
        SELECT c
        FROM Copy c
        JOIN FETCH c.book b
        WHERE c.deletedAt IS NULL
    """)
    Page<Copy> findAllActive(Pageable pageable);

    @Query(
        value = """
            SELECT c
            FROM Copy c
            JOIN c.book b
            WHERE c.deletedAt IS NULL
              AND (
                    LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :search, '%'))
              )
        """,
        countQuery = """
            SELECT COUNT(c)
            FROM Copy c
            JOIN c.book b
            WHERE c.deletedAt IS NULL
              AND (
                    LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :search, '%'))
              )
        """
    )
    Page<Copy> searchActiveCopies(
        @Param("search") String search,
        Pageable pageable
    );

}
