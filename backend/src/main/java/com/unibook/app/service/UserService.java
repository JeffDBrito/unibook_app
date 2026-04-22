package com.unibook.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.model.Person;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.PersonRepository;
import com.unibook.app.repository.RoleRepository;
import com.unibook.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(String name, String email, String login, String password, List<Long> roleIds) {

        // create Person
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        personRepository.save(person);

        // create User
        User user = new User();
        if(login == null || login.isEmpty()) {
            login = email; // if login is empty, use email as login
        }
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setPerson(person);
        
        for (Long rId : roleIds) {
            Role r = roleRepository.findById(rId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + rId));
            user.getRoles().add(r);
        }
        
        System.out.println("Creating user with login: " + login + ", roles: " + user.getRoles().stream().map(Role::getTitle).toList());

        User savedUser = userRepository.save(user);
        
        return toResponse(savedUser);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    
    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setLogin(user.getLogin());

        response.setName(user.getPerson().getName());
        response.setEmail(user.getPerson().getEmail());

        response.setSuperuser(user.isSuperuser());

        String roleTitles = user.getRoles().stream()
                .map(Role::getTitle)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No Roles");
        System.out.println("Mapping user to response: " + user.getLogin() + ", roles: " + roleTitles);
        response.setRoles(roleTitles);

        return response;
    }

    public UserResponse login(String login, String password) {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return toResponse(user);
    }

}
