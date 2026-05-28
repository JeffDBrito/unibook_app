package com.unibook.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.inventory.CreateInventoryRequest;
import com.unibook.app.dto.request.inventory.PartialUpdateInventoryRequest;
import com.unibook.app.dto.request.inventory.UpdateInventoryRequest;
import com.unibook.app.dto.response.InventoryResponse;
import com.unibook.app.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    // List inventories
    @GetMapping
    @Operation(summary = "List inventories", description = "Retrieves a list of all inventories and returns their details.", tags = {"Inventory Endpoints"})
    public List<InventoryResponse> getAll() {
        return inventoryService.findAll();
    }

    // Create inventory
    @PostMapping
    @Operation(summary = "Create a new inventory", description = "Creates a new inventory with the provided details and returns the created inventory.", tags = {"Inventory Endpoints"})
    public InventoryResponse create(@Valid @RequestBody CreateInventoryRequest request) {
        return inventoryService.createInventory(request);
    }

    // Partial update
    @PatchMapping("/{id}")
    @Operation(summary = "Partial update inventory", description = "Partially updates an existing inventory with the provided details and returns the updated inventory.", tags = {"Inventory Endpoints"})
    public InventoryResponse partialUpdate( @PathVariable Long id, @Valid @RequestBody PartialUpdateInventoryRequest request ) {
        return inventoryService.update(id, request, true);
    }

    // Full update
    @PutMapping("/{id}")
    @Operation(summary = "Update inventory", description = "Updates an existing inventory with the provided details and returns the updated inventory.", tags = {"Inventory Endpoints"})
    public InventoryResponse fullUpdate( @PathVariable Long id, @Valid @RequestBody UpdateInventoryRequest request) {
        return inventoryService.update(id, request);
    }

    // Get inventory by id
    @GetMapping("/{id}")
    @Operation(summary = "Get inventory by id", description = "Retrieves a inventory by their id and returns the inventory details.", tags = {"Inventory Endpoints"})
    public InventoryResponse getById(@PathVariable Long id) {
        return inventoryService.findById(id);
    }

    // Get inventory by location
    @GetMapping("/location")
    @Operation(summary = "Find inventory by location", description = "Finds an inventory by sector, shelf, row and slot.", tags = {"Inventory Endpoints"})
    public InventoryResponse getByLocation(
        @RequestParam String sector,
        @RequestParam String shelf,
        @RequestParam int row,
        @RequestParam int slot
    ){
        return inventoryService.findByLocation(sector, shelf, row, slot);
    }

    // Delete inventory by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete inventory by id", description = "Deletes a inventory by their id and returns no content.", tags = {"Inventory Endpoints"})
    public void deleteById(@PathVariable Long id) {
        inventoryService.deleteById(id);
    }

    // Restore inventory by id
    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore inventory by id", description = "Restores a previously deleted inventory by their id and returns the restored inventory details.", tags = {"Inventory Endpoints"})
    public InventoryResponse restoreById(@PathVariable Long id) {
        return inventoryService.restoreById(id);
    }

}
