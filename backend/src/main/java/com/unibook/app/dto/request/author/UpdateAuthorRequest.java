package com.unibook.app.dto.request.author;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAuthorRequest {
    
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
    @NotBlank(message = "Name is required")
    @Schema(example = "John Doe")
    private String name;
    
    @Size(min = 1, max = 500, message = "Biography must contain between 1 and 500 characters")
    @NotBlank(message = "Biography is required")
    @Schema(example = "John Doe is a renowned author known for his compelling storytelling.")
    private String biography;

    @Past(message = "Birth date must be in the past")
    @NotNull(message = "Birth date is required")
    @Schema(example = "2000-12-24")
    private LocalDate birthDate;

}
