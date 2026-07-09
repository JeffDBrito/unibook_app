package com.unibook.app.mapper;


import java.util.List;
import java.util.stream.Collectors;

import com.unibook.app.dto.response.RoleResponse;
import com.unibook.app.model.Role;

public class RoleMapper {
 
    /**
     * Convert Role instance to RoleResponse
     * @param role
     * @return RoleResponse
     */ 
    public static RoleResponse toResponse(Role role) {
        List<String> permissionNames = role.getPermissions().stream()
                .map(permission -> permission.getTitle())
                .collect(Collectors.toList());

        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getTitle());
        response.setPermissions(permissionNames);
        return response;
    }

}
