package com.unibook.app.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.unibook.app.model.User;

@Component
public class SecurityService {

    public User getCurrentUser() {
        return (User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }
}
