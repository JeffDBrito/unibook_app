package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "A")
    private String sector;

    @Schema(example = "2")
    private String shelf;

    @Schema(example = "4")
    private int row;

    @Schema(example = "12")
    private int slot;

    @Schema(example = "section_A1, shelf_2, row_4, slot_12")
    private String addressString;

    // Nested Object (Doesn't need a specific schema example)
    private CopyResponse copy;

}
