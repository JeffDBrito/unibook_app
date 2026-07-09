package com.unibook.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.SignupRequest;
import com.unibook.app.dto.request.user.CreateUserRequest;
import com.unibook.app.dto.response.RoleResponse;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.exceptions.BadCredentialsException;
import com.unibook.app.model.User;
import com.unibook.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Process login request
     * @param login
     * @param password
     * @return String
     */
    public String login(String login, String password) {
        User user = userRepository.findByLoginAndDeletedAtIsNull(login)
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials")); // User not found
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return jwtService.generateToken(user);
    }

    public UserResponse signup(SignupRequest request){       
        String name = request.getName();
        String email = request.getEmail();
        String login = request.getLogin();
        String password = request.getPassword();
        LocalDate birthDate = request.getBirthDate();

        RoleResponse guest = roleService.findByTitle("GUEST");
        List<Long> roles = List.of(guest.getId());

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, birthDate, login, password, roles);
        UserResponse response = userService.createUser(createUserRequest);

        return response;
    }
}