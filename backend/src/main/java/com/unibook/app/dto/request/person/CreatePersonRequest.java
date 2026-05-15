package com.unibook.app.dto.request.person;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePersonRequest {
    
    @Schema(example = "John Doe")
    private String name;
    
    @Schema(example = "john@email.com")
    private String email;

    @Schema(example = "2000-12-24")
    private LocalDate birthDate;

}
