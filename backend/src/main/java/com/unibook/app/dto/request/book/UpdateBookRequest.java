package com.unibook.app.dto.request.book;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    
    @Schema(example = "The Great Gatsby")
    private String title;
    
    @Schema(example = "978-0-7432-7356-5")
    private String isbn;
    
    @Schema(example = "1925")
    private Integer publicationYear;
    
    @Schema(example = "A classic American novel set in the Jazz Age.")
    private String description;
    
    @Schema(example = "1")
    private Long publisherId;

    @Schema(example = "[1, 2]")
    private List<Long> authorIds;

    @Schema(example = "[1, 3]")
    private List<Long> categoryIds;

}
