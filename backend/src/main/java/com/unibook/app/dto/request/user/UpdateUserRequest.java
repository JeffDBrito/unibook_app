package com.unibook.app.dto.request.user;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    // Person details
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

    // User details
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 15, message = "Login must contain between 3 and 15 characters")
    @Schema(example = "john123")
    private String login;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    @Schema(example = "123456")
    private String password;
    
    @NotEmpty(message = "At least one role is required")
    @Schema(example = "[1, 2]")
    private List<Long> roleIds;
    
}
