package com.payment.app.service;

import com.payment.app.model.User;
import com.payment.app.repository.UserRepository;
import org.springframework.stereotype.Service;

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
