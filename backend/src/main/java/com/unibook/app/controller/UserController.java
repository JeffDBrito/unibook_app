package com.unibook.app.controller;

import org.springframework.web.bind.annotation.*;

import com.unibook.app.dto.request.CreateUserRequest;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // List users
    @GetMapping
    @Operation(summary = "List users", description = "Retrieves a list of all users and returns their details.", tags = {"User Endpoints"})
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    // Get user by id
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID and returns the user details.", tags = {"User Endpoints"})
    public UserResponse getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Create user
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details and returns the created user.", tags = {"User Endpoints"})
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(
                request.getName(),
                request.getEmail(),
                request.getLogin(),
                request.getPassword(),
                request.getRoleIds()
        );
    }
}
