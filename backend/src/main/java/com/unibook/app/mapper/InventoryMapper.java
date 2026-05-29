package com.unibook.app.mapper;

import com.unibook.app.dto.request.inventory.PartialUpdateInventoryRequest;
import com.unibook.app.dto.request.inventory.UpdateInventoryRequest;
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
        response.setAddressString(
            "section_" + inventory.getSector() +
            ", shelf_" + inventory.getShelf() +
            ", row_" + inventory.getRow() +
            ", slot_" + inventory.getSlot()
        );        

        Copy copy = inventory.getCopy();
        if(copy != null){
            response.setCopy(CopyMapper.toResponse(copy));
        }

        return response;
    } 

    /**
     * Convert Full update dto to Partial update dto
     * @param request
     * @return
     */
    public static PartialUpdateInventoryRequest toPartialUpdate(UpdateInventoryRequest request){
        PartialUpdateInventoryRequest partial = new PartialUpdateInventoryRequest();
        partial.setSector(request.getSector());
        partial.setShelf(request.getShelf());
        partial.setRow(request.getRow());
        partial.setSlot(request.getSlot());
        return partial;
    }

}
