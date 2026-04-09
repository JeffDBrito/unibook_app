package com.unibook.app.service;

import org.springframework.stereotype.Service;

import com.unibook.app.model.User;
import com.unibook.app.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> listUsers() {
        return repository.findAll();
    }

    public User createUser(User user) {
        return repository.save(user);
    }
}
