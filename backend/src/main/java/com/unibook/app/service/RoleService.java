package com.unibook.app.service;

import com.unibook.app.dto.response.RoleResponse;
import com.unibook.app.model.Permission;
import com.unibook.app.model.Role;
import com.unibook.app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public RoleResponse findById(Long id) {
        return roleRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public RoleResponse findByTitle(String title) {
        return roleRepository.findByTitle(title)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Role not found with title: " + title));
    }

    public RoleResponse createRole(String title, List<Long> permissionIds) {
        Role role = new Role();
        role.setTitle(title);

        List<Permission> permissions = permissionIds.stream()
                .map(id -> {
                    Permission permission = new Permission();
                    permission.setId(id);
                    return permission;
                })
                .collect(java.util.stream.Collectors.toList());

        role.setPermissions(permissions);
        return toResponse(roleRepository.save(role));
    }

    public void deleteById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        
        roleRepository.delete(role);
    }

    private RoleResponse toResponse(Role role) {
        List<String> permissionNames = role.getPermissions().stream()
                .map(Permission::getTitle)
                .collect(java.util.stream.Collectors.toList());

        RoleResponse response = new RoleResponse();
        response.setName(role.getTitle());
        response.setPermissions(permissionNames);
        return response;
    }

    @Transactional
    public void assignPermissions(Role role, List<Permission> permissions) {
        permissions.forEach(permission -> {
            if (!role.getPermissions().contains(permission)) {
                role.getPermissions().add(permission);
            }
        });

        roleRepository.save(role);
    }

    @Transactional
    public void assignPermissionsByRoleName(String roleName, List<Permission> permissions) {
        Role role = roleRepository.findByTitle(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        assignPermissions(role, permissions);
    }
}