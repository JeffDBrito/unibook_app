package com.unibook.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unibook.app.enums.FineStatus;
import com.unibook.app.model.Fine;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByStatus(FineStatus status);
    Optional<Fine> findByLoanId(Long loanId);
}
