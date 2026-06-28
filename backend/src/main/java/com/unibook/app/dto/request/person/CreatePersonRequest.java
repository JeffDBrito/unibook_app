package com.unibook.app.dto.request.person;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePersonRequest {
    
    @NotBlank(message = "Name is required")
    @Schema(example = "John Doe")
    private String name;
    
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Schema(example = "john@email.com")
    private String email;

    @Past(message = "Birth date must be in the past")
    @NotNull(message = "Birth date is required")
    @Schema(example = "2000-12-24")
    private LocalDate birthDate;

}
