package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.PermissionResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.PermissionMapper;
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
            .map(PermissionMapper::toResponse)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find Permission by id
     * @param id
     * @return PermissionResponse
     */
    public PermissionResponse findById(Long id) {
        return PermissionMapper.toResponse(permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id)));
    }

}
