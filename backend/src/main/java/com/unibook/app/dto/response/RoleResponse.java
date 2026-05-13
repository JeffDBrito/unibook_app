package com.unibook.app.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Admin")
    private String name;
    
    @Schema(example = "[Create example, Read Example, Delete Example]")
    private List<String> permissions;

}
