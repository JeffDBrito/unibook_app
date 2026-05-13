package com.unibook.app.dto.request.role;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {
    
    @Schema(example = "Admin")
    private String title;

    @Schema(example = "[1, 2, 3]")
    private List<Long> permissionIds;

}
