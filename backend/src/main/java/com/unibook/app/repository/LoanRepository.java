package com.unibook.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unibook.app.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);
    Optional<Loan> findByCopyId(Long copyId);
}
