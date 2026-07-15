package com.unibook.app.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.role.CreateRoleRequest;
import com.unibook.app.dto.request.role.UpdateRoleRequest;
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
    @PreAuthorize("hasAuthority('ROLE_LIST')")
    @GetMapping
    @Operation(summary = "List roles", description = "Retrieves a list of all roles and returns their details.", tags = {"Role Endpoints"})
    public List<RoleResponse> getAllRoles() {
        return roleService.findAll();
    }

    // create role
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a new role with the provided details and returns the created role.", tags = {"Role Endpoints"})
    public RoleResponse createRole(@RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

    // Get role by ID
    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID", description = "Retrieves a role by its ID and returns its details.", tags = {"Role Endpoints"})
    public RoleResponse getRoleById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    // Delete by id
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role by ID", description = "Deletes a role by its unique ID and returns a confirmation message.", tags = {"Role Endpoints"})
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);        
    }

    // Get role by title
    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping("/title/{title}")
    @Operation(summary = "Get role by title", description = "Retrieves a role by its title and returns its details.", tags = {"Role Endpoints"})
    public RoleResponse getRoleByTitle(@PathVariable String title) {
        return roleService.findByTitle(title);
    }

    // Restore role
    @PreAuthorize("hasAuthority('ROLE_RESTORE')")
    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore Role by id", description = "Restores a previously deleted Role by their id and returns the restored Role details.", tags = {"Role Endpoints"})
    public RoleResponse restore(@PathVariable Long id){
        return roleService.restoreById(id);
    }

    // Partial update
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update Role", description = "Partially updates an existing Role with the provided details and returns the updated Role.", tags = {"Role Endpoints"})
    public RoleResponse partialUpdate(@PathVariable Long id, @RequestBody UpdateRoleRequest request){
        return roleService.update(id, request, true);
    }

    // Full update
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update Role", description = "Updates an existing Role with the provided details and returns the updated Role.", tags = {"Role Endpoints"})
    public RoleResponse fullUpdate(@PathVariable Long id, @RequestBody UpdateRoleRequest request){
        return roleService.update(id, request, false);
    }

}
