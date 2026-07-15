package com.unibook.app.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.unibook.app.dto.request.user.CreateUserRequest;
import com.unibook.app.dto.request.user.PartialUpdateUserRequest;
import com.unibook.app.dto.request.user.UpdateUserRequest;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('USER_LIST')")
    // List users
    @GetMapping
    @Operation(summary = "List users", description = "Retrieves a list of all users and returns their details.", tags = {"User Endpoints"})
    public Page<UserResponse> findAll(
        @RequestParam(required = false, defaultValue = "") String search,
        @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return userService.findAll(search, pageable);
    }

    // Get user by id
    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID and returns the user details.", tags = {"User Endpoints"})
    public UserResponse getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Delete by id
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", description = "Deletes a user by their unique ID", tags = {"User Endpoints"})
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);        
    }

    // Restore user
    @PreAuthorize("hasAuthority('USER_RESTORE')")
    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore user by ID", description = "Restore a user by their unique ID and returns the restored user.", tags = {"User Endpoints"})
    public UserResponse restoreUser(@PathVariable Long id){
        return userService.restoreById(id);
    }

    // Create user
    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details and returns the created user.", tags = {"User Endpoints"})
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    // Partial update
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping("/{id}")
    @Operation(summary = "Partial update User", description = "Partially updates an existing user with the provided details and returns the updated user.", tags = {"User Endpoints"})
    public UserResponse partialUpdate(@PathVariable Long id, @Valid @RequestBody PartialUpdateUserRequest request){
        return userService.update(id, request, true);
    }

    // Full update
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user with the provided details and returns the updated user.", tags = {"User Endpoints"})
    public UserResponse fullUpdate(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request){
        return userService.update(id, request);
    }

}
