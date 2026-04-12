package com.unibook.app.service;

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

    @Transactional
    public void assignPermissions(Role role, List<Permission> permissions) {

        // evita duplicação
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