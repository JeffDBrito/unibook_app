package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.InventoryResponse;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Inventory;
import com.unibook.app.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final CopyService copyService;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create inventory
     * @param sector
     * @param shelf
     * @param row
     * @param slot
     * @return InventoryResponse
     */ //TODO: User CreateRequest dto
    public InventoryResponse createInventory(String sector, String shelf, int row, int slot){
        Inventory inventory = new Inventory();
        inventory.setSector(sector);
        inventory.setShelf(shelf);
        inventory.setRow(row);
        inventory.setSlot(slot);

        return toResponse(inventoryRepository.save(inventory));
    }

    /**
     * Delete Inventory by id
     * @param id
     */
    public void deleteById(Long id){
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.softDelete();
        inventoryRepository.save(inventory);
    }



    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * List all Inventories
     * @return List<InventoryResponse>
     */
    public List<InventoryResponse> findAll() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream().map(this::toResponse).toList();
    }

    /**
     * Find Inventory by id
     * @param id
     * @return InventoryResponse
     * @throws RuntimeException
     */
    public InventoryResponse findById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        return toResponse(inventory);
    }

    /**
     * Find Inventory by Location details
     * @param sector
     * @param shelf
     * @param row
     * @param slot
     * @return InventoryResponse
     */
    public InventoryResponse findByLocation(String sector, String shelf, int row, int slot) {
        Inventory inventory = inventoryRepository.findBySectorAndShelfAndRowAndSlot(sector,shelf,row,slot)
            .orElseThrow(() ->
                new RuntimeException("Inventory not found")
            );

        return toResponse(inventory);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Inventory instance to InventoryResponse dto
     * @param inventory
     * @return InventoryResponse
     */ // TODO: Create a Mapper
    private InventoryResponse toResponse(Inventory inventory){
        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setSector(inventory.getSector());
        response.setShelf(inventory.getShelf());
        response.setRow(inventory.getRow());
        response.setSlot(inventory.getSlot());

        Copy copy = inventory.getCopy();
        if(copy != null){
            response.setCopy(copyService.toResponse(copy));
        }

        return response;
    }    
    
}
