package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.enums.CopyStatus;
import com.unibook.app.model.Copy;

public interface CopyRepository extends JpaRepository<Copy, Long>{
    
    Boolean existsByCode(String code);
    Boolean existsByBookIdAndDeletedAtIsNull(Long bookId);
    Optional<Copy> findFirstByBookIdAndStatusAndDeletedAtIsNull(
        Long bookId,
        CopyStatus status
    );

}
