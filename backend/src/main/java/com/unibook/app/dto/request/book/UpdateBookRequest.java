package com.unibook.app.dto.request.book;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    
    @Size(min = 1, max = 100, message = "Title must contain between 1 and 100 characters")
    @NotBlank(message = "Title is required")
    @Schema(example = "The Great Gatsby")
    private String title;
    
    @NotBlank(message = "ISBN is required")
    @Schema(example = "978-0-7432-7356-5")
    private String isbn;
    
    @NotBlank(message = "Publication Year is required")
    @Schema(example = "1925")
    private Integer publicationYear;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 150, message = "Description must contain between 10 and 150 characters")
    @Schema(example = "A classic American novel set in the Jazz Age.")
    private String description;
    
    @NotNull(message = "Publisher is required")
    @Schema(example = "1")
    private Long publisherId;

    @NotBlank(message = "At least one actor is required")
    @Schema(example = "[1, 2]")
    private List<Long> authorIds;

    @NotBlank(message = "At least one category is required")
    @Schema(example = "[1, 3]")
    private List<Long> categoryIds;

}
