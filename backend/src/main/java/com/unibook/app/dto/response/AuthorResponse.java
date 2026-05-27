package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponse {
    
    @Schema(example = "1")
    private Long id;
    
    @Schema(example = "John Doe is a renowned author known for his compelling storytelling.")
    private String biography;

    private PersonResponse person;

}
