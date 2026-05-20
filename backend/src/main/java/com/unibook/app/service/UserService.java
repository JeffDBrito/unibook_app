package com.unibook.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.user.CreateUserRequest;
import com.unibook.app.dto.request.user.PartialUpdateUserRequest;
import com.unibook.app.dto.request.user.UpdateUserRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.exceptions.BadRequestException;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.model.Person;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.PersonRepository;
import com.unibook.app.repository.RoleRepository;
import com.unibook.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     */
    public UserResponse createUser(CreateUserRequest request) {

        String login = request.getLogin();
        String email = request.getEmail();
        String password = request.getPassword();

        if(personRepository.existsByEmail(email)){
            throw new BadRequestException("Email already exists");
        }

        if(userRepository.existsByLogin(login)){
            throw new BadRequestException("Login already exists");
        }

        // create Person
        Person person = new Person();
        person.setName(request.getName());
        person.setEmail(request.getEmail());
        person.setBirthDate(request.getBirthDate());
        personRepository.save(person);

        // create User
        User user = new User();
        if(login == null || login.isEmpty()) {
            login = email; // if login is empty, use email as login
        }
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setPerson(person);
        
        if(request.getRoleIds().size() > 0){
            for (Long roleId : request.getRoleIds()) {
                Role r = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));
                user.getRoles().add(r);
            }
        }else{
            Role guest = roleRepository.findByTitle("GUEST").get();
            user.getRoles().add(guest);
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
    public UserResponse update(Long id, PartialUpdateUserRequest request, boolean partial){
        User user = userRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found")
            );

        Person person = user.getPerson();

        if(!partial || request.getName() != null){
            person.setName(request.getName());
        }

        if(!partial || request.getEmail() != null){
            if(request.getEmail() != null && userRepository.existsByPersonEmail(request.getEmail()) && !person.getEmail().equals(request.getEmail())){
                throw new BadRequestException("Email already exists");
            }

            person.setEmail(request.getEmail());
        }

        if(!partial || request.getBirthDate() != null){
            person.setBirthDate(request.getBirthDate());
        }

        if(!partial || request.getLogin() != null){
            if(request.getLogin() != null && userRepository.existsByLogin(request.getLogin()) && !user.getLogin().equals(request.getLogin())){
                throw new BadRequestException("Login already exists");
            }
            user.setLogin(request.getLogin());
        }

        if(!partial || request.getPassword() != null){
            if(request.getPassword() == null || request.getPassword().isBlank()){
                throw new BadRequestException("Password cannot be empty");
            }

            user.setPassword(
                passwordEncoder.encode(request.getPassword())
            );
        }

        if(!partial || request.getRoleIds() != null){
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
            if(roles.size() != request.getRoleIds().size()){
                throw new BadRequestException("One or more roles were not found");
            }
            user.setRoles(roles);
        }

        return toResponse(userRepository.save(user));
    }

    /**
     * Convert UpdateUserRequest to PartialUpdateUserRequest
     * @param id
     * @param request
     * @param partial
     * @return UserResponse
     */
    public UserResponse update(Long id, UpdateUserRequest request, boolean partial){
        PartialUpdateUserRequest partialRequest = new PartialUpdateUserRequest();
        partialRequest.setName(request.getName());
        partialRequest.setEmail(request.getEmail());
        partialRequest.setLogin(request.getLogin());
        partialRequest.setBirthDate(request.getBirthDate());
        partialRequest.setPassword(request.getPassword());
        partialRequest.setRoleIds(request.getRoleIds());
        return update(id, partialRequest, partial);
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
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));

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
    public UserResponse toResponse(User user) {
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
