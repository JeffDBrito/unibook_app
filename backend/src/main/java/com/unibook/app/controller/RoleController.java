package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.CreateRoleRequest;
import com.unibook.app.dto.response.RoleResponse;
import com.unibook.app.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/roles")
public class RoleController {
    
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // List Roles
    @GetMapping
    @Operation(summary = "List roles", description = "Retrieves a list of all roles and returns their details.", tags = {"Role Endpoints"})
    public List<RoleResponse> getAllRoles() {
        return roleService.findAll();
    }

    // create role
    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a new role with the provided details and returns the created role.", tags = {"Role Endpoints"})
    public RoleResponse createRole(@RequestBody CreateRoleRequest request) {
        return roleService.createRole(request.getName(), request.getPermissionIds());
    }

    // Get role by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID", description = "Retrieves a role by its ID and returns its details.", tags = {"Role Endpoints"})
    public RoleResponse getRoleById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    // Get role by title
    @GetMapping("/title/{title}")
    @Operation(summary = "Get role by title", description = "Retrieves a role by its title and returns its details.", tags = {"Role Endpoints"})
    public RoleResponse getRoleByTitle(@PathVariable String title) {
        return roleService.findByTitle(title);
    }


}
