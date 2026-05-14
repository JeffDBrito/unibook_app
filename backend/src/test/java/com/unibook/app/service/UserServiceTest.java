package com.unibook.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unibook.app.dto.request.user.CreateUserRequest;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.model.Role;
import com.unibook.app.repository.RoleRepository;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldCreateUserSuccessfully() {
        Role role = new Role();
        role.setTitle("TEST_ROLE");
        role = roleRepository.save(role);

        UserResponse user = userService.createUser(new CreateUserRequest(
            "Test",
            "test@email.com",
            LocalDate.of(2024,12,24),
            "test",
            "123",
            List.of(role.getId()))
        );

        assertNotNull(user);
        assertEquals("test", user.getLogin());
    }

}
