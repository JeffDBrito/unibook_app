package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.PermissionResponse;
import com.unibook.app.model.Permission;
import com.unibook.app.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    
    private final PermissionRepository permissionRepository;

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all Permissions
     * @return List<PermissionResponse>
     */
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream()
            .map(this::toResponse)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find Permission by id
     * @param id
     * @return PermissionResponse
     */
    public PermissionResponse findById(Long id) {
        return toResponse(permissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id)));
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Permission instance to PermissionResponse
     * @param permission
     * @return PermissionResponse
     */
    public PermissionResponse toResponse(Permission permission) {
        PermissionResponse response = new PermissionResponse();
        response.setTitle(permission.getTitle());
        response.setDescription(permission.getDescription());
        return response;
    }

}
