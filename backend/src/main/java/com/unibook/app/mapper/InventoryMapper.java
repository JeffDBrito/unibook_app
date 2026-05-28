package com.unibook.app.mapper;

import com.unibook.app.dto.response.InventoryResponse;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Inventory;

public class InventoryMapper {
    
    /**
     * Convert Inventory instance to InventoryResponse dto
     * @param inventory
     * @return InventoryResponse
     */ 
    public static InventoryResponse toResponse(Inventory inventory){
        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setSector(inventory.getSector());
        response.setShelf(inventory.getShelf());
        response.setRow(inventory.getRow());
        response.setSlot(inventory.getSlot());

        Copy copy = inventory.getCopy();
        if(copy != null){
            response.setCopy(CopyMapper.toResponse(copy));
        }

        return response;
    } 

}
