package com.payment.app.controller;

import com.payment.app.model.User;
import com.payment.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> list() {
        return service.listUsers();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }
}
