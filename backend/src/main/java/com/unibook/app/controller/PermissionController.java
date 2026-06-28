package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.response.PermissionResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.service.PermissionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // List permissions
    @GetMapping
    @Operation(summary = "List permissions", description = "Retrieves a list of all permissions and returns their details.", tags = {"Permission Endpoints"})
    public List<PermissionResponse> getAllPermissions() {
        return permissionService.findAll();
    }

    // Get permission by id
    @GetMapping("/{id}")
    @Operation(summary = "Get permission by ID", description = "Retrieves a permission by its unique ID and returns the permission details.", tags = {"Permission Endpoints"})
    public PermissionResponse getPermissionById(@PathVariable Long id) {
        return permissionService.findById(id);
    }

    // Get permission by title
    @GetMapping("/title/{title}")
    @Operation(summary = "Get permission by title", description = "Retrieves a permission by its title and returns the permission details.", tags = {"Permission Endpoints"})
    public PermissionResponse getPermissionByTitle(@PathVariable String title) {
        return permissionService.findAll().stream()
                .filter(permission -> permission.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with title: " + title));
    }


}
