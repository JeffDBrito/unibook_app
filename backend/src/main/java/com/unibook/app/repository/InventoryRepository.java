package com.unibook.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
    Optional<Inventory> findBySectorAndShelfAndRowAndSlot(String sector, String shelf, int row, int slot);

}
