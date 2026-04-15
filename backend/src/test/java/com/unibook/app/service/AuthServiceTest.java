package com.unibook.app.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unibook.app.exceptions.BadCredentialsException;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    
    @Test
    void shouldLoginSuccessfully() {
        String token = authService.login("admin", "admin");
        assertNotNull(token);
    }

    @Test
    void shouldFailLoginWithWrongPassword() {
        assertThrows(BadCredentialsException.class, () -> {
            authService.login("admin", "nimda");
        });
    }

}
