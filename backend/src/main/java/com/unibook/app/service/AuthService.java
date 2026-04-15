package com.unibook.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unibook.app.exceptions.BadCredentialsException;
import com.unibook.app.model.User;
import com.unibook.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String login, String password) {

        System.out.println("Attempting login for user: " + login + " with password: " + password);

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials")); // User not found

        System.out.println("User found: " + user.getLogin() + ", stored password hash: " + user.getPassword() + ", provided password: " + password + ", password matches: " + passwordEncoder.matches(password, user.getPassword()));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return jwtService.generateToken(user);
    }
}