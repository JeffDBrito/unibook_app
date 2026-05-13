package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponse {
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Read Example")
    private String title;

    @Schema(example = "Allow user to read examples.")
    private String description;

}
