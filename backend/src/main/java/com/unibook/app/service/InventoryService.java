package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.inventory.CreateInventoryRequest;
import com.unibook.app.dto.request.inventory.PartialUpdateInventoryRequest;
import com.unibook.app.dto.request.inventory.UpdateInventoryRequest;
import com.unibook.app.dto.response.InventoryResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.InventoryMapper;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Inventory;
import com.unibook.app.repository.CopyRepository;
import com.unibook.app.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final CopyRepository copyRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create inventory
     * @param request
     * @return InventoryResponse
     */
    public InventoryResponse createInventory(CreateInventoryRequest request){
        Inventory inventory = new Inventory();
        inventory.setSector(request.getSector());
        inventory.setShelf(request.getShelf());
        inventory.setRow(request.getRow());
        inventory.setSlot(request.getSlot());

        return InventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    /**
     * Delete Inventory by id
     * @param id
     */
    public void deleteById(Long id){
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventory.softDelete();
        inventoryRepository.save(inventory);
    }

    /**
     * Restore Inventory by id
     * @param id
     * @return InventoryResponse
     */
    public InventoryResponse restoreById(Long id){
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventory.restore();
        return InventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    /**
     * Update Inventory
     * @param id
     * @param request
     * @param partial
     * @return InventoryResponse
     */
    public InventoryResponse update(Long id, PartialUpdateInventoryRequest request, boolean partial){

        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if(!partial || request.getSector() != null){
            inventory.setSector(request.getSector());
        }

        if(!partial || request.getShelf() != null){
            inventory.setShelf(request.getShelf());
        }

        if(!partial || request.getRow() != null){
            inventory.setRow(request.getRow());
        }

        if(!partial || request.getSlot() != null){
            inventory.setSlot(request.getSlot());
        }

        if(!partial || request.getCopyId() != null){
            Copy copy = copyRepository.findById(request.getCopyId())
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

            inventory.setCopy(copy);
        }

        return InventoryMapper.toResponse(inventoryRepository.save(inventory));

    }

    public InventoryResponse update(Long id, UpdateInventoryRequest request){
        return update(id, InventoryMapper.toPartialUpdate(request), false);
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
        return inventories.stream().map(InventoryMapper::toResponse).toList();
    }

    /**
     * Find Inventory by id
     * @param id
     * @return InventoryResponse
     * @throws RuntimeException
     */
    public InventoryResponse findById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
        return InventoryMapper.toResponse(inventory);
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
                new ResourceNotFoundException("Inventory not found")
            );

        return InventoryMapper.toResponse(inventory);
    } 
    
}
