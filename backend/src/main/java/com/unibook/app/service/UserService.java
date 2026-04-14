package com.unibook.app.service;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.model.Person;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.PersonRepository;
import com.unibook.app.repository.RoleRepository;
import com.unibook.app.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PersonRepository personRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    public UserResponse createUser(String name, String email, String login, String password, Long roleId, boolean superuser) {

        // create Person
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        personRepository.save(person);

        // fetch Role (GUEST by default)
        Role role = (roleId != null)
        ? roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId))
        : roleRepository.findByTitle("VISITANTE")
            .orElseThrow(() -> new RuntimeException("Default role not found"));

        // create User
        User user = new User();
        if(login == null || login.isEmpty()) {
            login = email; // if login is empty, use email as login
        }
        user.setLogin(login);
        user.setPassword(password); // TODO: hash password
        user.setPerson(person);
        user.setRole(role);
        user.setSuperuser(superuser);
        System.out.println("Creating user with login: " + login + ", role: " + role.getTitle() + ", superuser: " + superuser);

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

        response.setRole(user.getRole().getTitle());
        response.setSuperuser(user.isSuperuser());

        return response;
    }
}
