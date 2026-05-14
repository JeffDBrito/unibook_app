package com.unibook.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.user.UpdateUserRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.model.Person;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.PersonRepository;
import com.unibook.app.repository.RoleRepository;
import com.unibook.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create User
     * @param name
     * @param email
     * @param login
     * @param password
     * @param roleIds
     * @return UserResponse
     */ //TODO: User CreateRequest dto
    public UserResponse createUser(String name, String email, String login, String password, LocalDate birthDate, List<Long> roleIds) {

        // create Person
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setBirthDate(birthDate);
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

    /**
     * Update User
     * @param id
     * @param request
     * @param partial
     * @return UserResponse
     */
    public UserResponse update(Long id, UpdateUserRequest request, boolean partial){
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!partial || request.getName() != null) {
            user.getPerson().setName(request.getName());
        }
        if (!partial || request.getEmail() != null) {
            user.getPerson().setEmail(request.getEmail());
        }
        if (!partial || request.getLogin() != null) {
            user.setLogin(request.getLogin());
        }
        if (!partial || request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (!partial || request.getRoleIds() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoleIds());
            if (roles.size() != request.getRoleIds().size()) {
                throw new RuntimeException("One or more roles not found");
            }
            user.setRoles(roles.stream().collect(java.util.stream.Collectors.toSet()));
        }

        return toResponse(userRepository.save(user));
    }

    /**
     * Delete User by id
     * @param id
     */
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.softDelete();
        userRepository.save(user);
    }

    /**
     * Restore User by id
     * @param id
     * @return UserResponse
     */
    public UserResponse restoreById(Long id){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: "+id));

        user.restore();
        userRepository.save(user);
        return toResponse(user);
            
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all users
     * @return List<UserResponse>
     */
    public List<UserResponse> findAll() {
        return userRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }
    
    /**
     * Find User by id
     * @param id
     * @return UserResponse
     */
    public UserResponse findById(Long id) {
        return userRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert User instance to UserResponse
     * @param user
     * @return UserResponse
     */ // TODO: Create a Mapper
    private UserResponse toResponse(User user) {
        PersonResponse person = new PersonResponse();
        person.setName(user.getPerson().getName());
        person.setEmail(user.getPerson().getEmail());
        person.setBirthDate(user.getPerson().getBirthDate());

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setLogin(user.getLogin());
        response.setPerson(person);

        String roleTitles = user.getRoles().stream()
            .map(Role::getTitle)
            .reduce((a, b) -> a + ", " + b)
            .orElse("No Roles");
        System.out.println("Mapping user to response: " + user.getLogin() + ", roles: " + roleTitles);
        response.setRoles(roleTitles);

        return response;
    }

}
