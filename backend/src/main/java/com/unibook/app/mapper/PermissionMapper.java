package com.unibook.app.mapper;

import com.unibook.app.dto.response.PermissionResponse;
import com.unibook.app.model.Permission;

public class PermissionMapper {
    
    /**
     * Convert Permission instance to PermissionResponse
     * @param permission
     * @return PermissionResponse
     */ 
    public static PermissionResponse toResponse(Permission permission) {
        PermissionResponse response = new PermissionResponse();
        response.setId(permission.getId());
        response.setTitle(permission.getTitle());
        response.setDescription(permission.getDescription());
        return response;
    }
}
