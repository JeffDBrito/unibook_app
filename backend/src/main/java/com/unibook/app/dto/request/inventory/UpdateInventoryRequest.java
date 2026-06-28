package com.unibook.app.dto.request.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryRequest {    
    
    @Size(min = 1, max = 10, message = "Sector must contain between 1 and 10 characters")
    @NotBlank(message = "Sector is required")
    @Schema(example = "A")
    private String sector;
    
    @Size(min = 1, max = 10, message = "Shelf must contain between 1 and 10 characters")
    @NotBlank(message = "Shelf is required")
    @Schema(example = "2")
    private String shelf;
    
    @NotNull(message = "Row is required")
    @Schema(example = "4")
    private Integer row;
    
    @NotNull(message = "Slot is required")
    @Schema(example = "12")
    private Integer slot;

    @NotNull(message = "Copy is required")
    @Schema(example = "3")
    private Long copyId;

}
