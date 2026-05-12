package com.unibook.app.dto.request.role;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {
    
    private String title;
    private List<Long> permissionIds;

}
