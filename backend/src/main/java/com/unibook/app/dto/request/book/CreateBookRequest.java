package com.unibook.app.dto.request.book;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    
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
