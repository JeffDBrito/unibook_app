package com.unibook.app.dto.request.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePersonRequest {
    
    @Schema(example = "John Doe")
    private String name;
    
    @Schema(example = "john@email.com")
    private String email;
    
}
