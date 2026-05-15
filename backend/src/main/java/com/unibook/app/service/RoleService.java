package com.unibook.app.service;

import com.unibook.app.dto.request.role.CreateRoleRequest;
import com.unibook.app.dto.request.role.UpdateRoleRequest;
import com.unibook.app.dto.response.RoleResponse;
import com.unibook.app.model.Permission;
import com.unibook.app.model.Role;
import com.unibook.app.repository.PermissionRepository;
import com.unibook.app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Role
     * @param title
     * @param permissionIds
     * @return RoleResponse
     */
    public RoleResponse createRole(CreateRoleRequest request) {
        Role role = new Role();
        role.setTitle(request.getTitle());

        Set<Permission> permissions = new HashSet<>(request.getPermissionIds().stream()
                .map(id -> {
                    Permission permission = new Permission();
                    permission.setId(id);
                    return permission;
                })
                .collect(java.util.stream.Collectors.toList()));

        role.setPermissions(permissions);
        return toResponse(roleRepository.save(role));
    }

    /**
     * Delete Role by id
     * @param id
     */
    public void deleteById(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        role.softDelete();
        roleRepository.save(role);        
    }

    /**
     * Restore Role by id
     * @param id
     * @return RoleResponse
     */
    public RoleResponse restoreById(Long id){
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        role.restore();
        return toResponse(roleRepository.save(role));
    }

    /**
     * Update Role
     * @param id
     * @param request
     * @param partial
     * @return RoleResponse
     */
    public RoleResponse update(Long id, UpdateRoleRequest request, boolean partial){

        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));

        if(!partial || request.getTitle() != null){
            role.setTitle(request.getTitle());
        }

        if(!partial || request.getPermissionIds() != null){
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionIds()));
            role.setPermissions(permissions);
        }

        return toResponse(roleRepository.save(role));
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all Roles
     * @return List<RoleResponse>
     */
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
            .map(this::toResponse)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find Role by id
     * @param id
     * @return RoleResponse
     */
    public RoleResponse findById(Long id) {
        return roleRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    /**
     * Find Role by title
     * @param title
     * @return RoleResponse
     */
    public RoleResponse findByTitle(String title) {
        return roleRepository.findByTitle(title)
            .map(this::toResponse)
            .orElseThrow(() -> new RuntimeException("Role not found with title: " + title));
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Role instance to RoleResponse
     * @param role
     * @return RoleResponse
     */ // TODO: Create a Mapper
    private RoleResponse toResponse(Role role) {
        List<String> permissionNames = role.getPermissions().stream()
                .map(Permission::getTitle)
                .collect(java.util.stream.Collectors.toList());

        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getTitle());
        response.setPermissions(permissionNames);
        return response;
    }

    /**
     * Assign permissions to Role
     * @param role
     * @param permissions
    */
    @Transactional
    public void assignPermissions(Role role, List<Permission> permissions) {
        permissions.forEach(permission -> {
            if (!role.getPermissions().contains(permission)) {
                role.getPermissions().add(permission);
            }
        });
        roleRepository.save(role);
    }

    /**
     * Assign permissions to Role by Role Name
     * @param roleName
     * @param permissions
     */
    @Transactional
    public void assignPermissionsByRoleName(String roleName, List<Permission> permissions) {
        Role role = roleRepository.findByTitle(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        assignPermissions(role, permissions);
    }
}