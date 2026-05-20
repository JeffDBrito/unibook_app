package com.unibook.app.dto.request.user;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialUpdateUserRequest {

    @Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
    @Schema(example = "John Doe")
    private String name;

    @Email(message = "Invalid email")
    @Schema(example = "john@email.com")
    private String email;

    @Size(min = 3, max = 20, message = "Login must contain between 3 and 20 characters")
    @Schema(example = "john123")
    private String login;
    
    @Past(message = "Birth date must be in the past")
    @Schema(example = "2000-12-24")
    private LocalDate birthDate;

    @Size(min = 6, message = "Password must contain at least 6 characters")
    @Schema(example = "123456")
    private String password;

    @Schema(example = "[1, 2]")
    private List<Long> roleIds;
    
}
