package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopyResponse {
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "A12B45")
    private String code;

    @Schema(example = "AVAILABLE")
    private String status;

    @Schema(example = "section_A1, shelf_2, row_4, slot_12")
    private String inventoryAddress;
    

    // Book Details

    // @Schema(example = "The Great Gatsby")
    // private String bookTitle;

    // @Schema(example = "1")
    // private Long bookId;

    // @Schema(example = "F. Scott Fitzgerald")
    // private String authors;

    private BookResponse book;

}
