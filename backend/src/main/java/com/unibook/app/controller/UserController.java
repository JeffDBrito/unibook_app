package com.unibook.app.controller;

import org.springframework.web.bind.annotation.*;

import com.unibook.app.dto.request.CreateUserRequest;
import com.unibook.app.dto.request.LoginRequest;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.model.User;
import com.unibook.app.service.UserService;

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
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    // Get user by id
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Create user
    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(
                request.getName(),
                request.getEmail(),
                request.getLogin(),
                request.getPassword(),
                request.getRoleId(),
                request.isSuperuser()
        );
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {
        System.out.println("Login request received for login: " + request.getLogin() + " with password: " + request.getPassword());
        return userService.login(request.getLogin(), request.getPassword());
    }
}
