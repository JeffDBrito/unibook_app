package com.unibook.app.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequest {
    
    @Size(min = 1, max = 30, message = "Title must contain between 1 and 30 characters")
    @NotBlank(message = "Title is required")
    @Schema(example = "Fiction")
    private String title;
 
    @Size(min = 1, max = 100, message = "Description must contain between 1 and 100 characters")
    @NotBlank(message = "Description is required")
    @Schema(example = "Fiction is a literary genre that includes imaginative or invented stories, often featuring characters and events that are not based on real life.")
    private String description;

}
