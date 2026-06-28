package com.unibook.app.mapper;

import com.unibook.app.dto.request.copy.PartialUpdateCopyRequest;
import com.unibook.app.dto.request.copy.UpdateCopyRequest;
import com.unibook.app.dto.response.CopyResponse;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Inventory;

public class CopyMapper {
    
    /**
     * Convert a Copy instance to CopyResponse
     * @param copy
     * @return
     */
    //
    public static CopyResponse toResponse(Copy copy) {

        CopyResponse response = new CopyResponse();

        response.setId(copy.getId());
        response.setCode(copy.getCode());
        response.setStatus(copy.getStatus().name());

        if (copy.getInventory() != null) {

            Inventory inventory = copy.getInventory();

            response.setInventoryAddress(
                "section_" + inventory.getSector() +
                ", shelf_" + inventory.getShelf() +
                ", row_" + inventory.getRow() +
                ", slot_" + inventory.getSlot()
            );
        }

        if (copy.getBook() != null) {           
            response.setBook(BookMapper.toResponse(copy.getBook()));
        }

        return response;
    }

    /**
     * Convert UpdateRequest to PartialUpdateRequest
     * @param request
     * @return PartialUpdateCopyRequest
     */
    public static PartialUpdateCopyRequest toPartialRequest(UpdateCopyRequest request){
        PartialUpdateCopyRequest partial = new PartialUpdateCopyRequest();

        return partial;
    }

}
