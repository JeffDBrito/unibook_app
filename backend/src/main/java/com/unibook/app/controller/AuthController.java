package com.unibook.app.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.LoginRequest;
import com.unibook.app.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token.", tags = {"Authentication"})
    public Map<String, String> login(@RequestBody LoginRequest request) {

        String token = authService.login(request.getLogin(), request.getPassword());

        return Map.of("token", token);
    }
}
