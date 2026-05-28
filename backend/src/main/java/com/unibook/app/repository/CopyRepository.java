package com.unibook.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Copy;

public interface CopyRepository extends JpaRepository<Copy, Long>{
    
    Boolean existsByCode(String code);

}
