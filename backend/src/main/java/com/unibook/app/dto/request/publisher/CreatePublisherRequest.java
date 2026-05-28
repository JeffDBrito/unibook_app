package com.unibook.app.dto.request.publisher;

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
public class CreatePublisherRequest {
    
    @Size(min = 1, max = 100, message = "Title must contain between 1 and 100 characters")
    @NotBlank(message = "Title is required")
    @Schema(example = "Penguin Random House")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 150, message = "Description must contain between 10 and 150 characters")
    @Schema(example = "Penguin Random House is a global book publishing company that publishes a wide range of fiction and non-fiction books across various genres.")
    private String description;

}
