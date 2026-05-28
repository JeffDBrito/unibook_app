package com.unibook.app.dto.request.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialUpdateInventoryRequest {    
    
    @Schema(example = "A")
    private String sector;
    
    @Schema(example = "2")
    private String shelf;
    
    @Schema(example = "4")
    private Integer row;
    
    @Schema(example = "12")
    private Integer slot;

}
