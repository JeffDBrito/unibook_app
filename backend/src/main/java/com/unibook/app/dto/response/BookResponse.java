package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "The Great Gatsby")
    private String title;
    
    @Schema(example = "978-0-7432-7356-5")
    private String isbn;
    
    @Schema(example = "1925")
    private Integer publicationYear;
    
    @Schema(example = "A classic American novel set in the Jazz Age.")
    private String description;
    
    @Schema(example = "Penguin Random House")
    private String publisher;

    @Schema(example = "John, Doe")
    private String authors;

    @Schema(example = "Romance, Fiction")
    private String categories;

}
