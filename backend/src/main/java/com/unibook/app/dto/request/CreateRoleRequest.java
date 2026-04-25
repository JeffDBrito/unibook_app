package com.unibook.app.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleRequest {
    
    @Schema(example = "Admin")
    private String name;

    @Schema(example = "[1, 2, 3]")
    private List<Long> permissionIds;

}