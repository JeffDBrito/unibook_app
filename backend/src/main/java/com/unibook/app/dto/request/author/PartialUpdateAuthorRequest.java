package com.unibook.app.dto.request.author;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialUpdateAuthorRequest {
    
    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "John Doe is a renowned author known for his compelling storytelling.")
    private String biography;

    @Schema(example = "2000-12-24")
    private LocalDate birthDate;

}
