package com.unibook.app.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unibook.app.enums.LoanRequestStatus;
import com.unibook.app.model.LoanRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {

    boolean existsByUserIdAndBookIdAndStatus(Long userId, Long bookId, LoanRequestStatus status);

    @Query("""
        SELECT lr.book.id
        FROM LoanRequest lr
        WHERE lr.user.id = :userId
        AND lr.status = 'PENDING'
        AND lr.book.id IN :bookIds
    """)
    Set<Long> findPendingBookIdsByUser(Long userId, Collection<Long> bookIds);

    Page<LoanRequest> findByStatus(LoanRequestStatus status, Pageable pageable);

    @Query("""
        SELECT lr
        FROM LoanRequest lr
        JOIN lr.user u
        JOIN u.person p
        JOIN lr.book b
        WHERE lr.status = :status
        AND (
            LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(u.login) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<LoanRequest> searchByStatus(@Param("status") LoanRequestStatus status, @Param("search") String search,
            Pageable pageable);

}
