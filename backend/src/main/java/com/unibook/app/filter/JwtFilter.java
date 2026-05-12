package com.unibook.app.filter;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unibook.app.model.Permission;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.UserRepository;
import com.unibook.app.service.JwtService;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token and validate it
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        // If the token is valid, set the authentication in the security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByLoginWithRoles(username).orElse(null);

            // If the user exists and the token is valid, set the authentication
            if (user != null && jwtService.isValid(token, user)) {
                var authorities = new ArrayList<SimpleGrantedAuthority>();
                Set<Role> roles = user.getRoles();
                // Add role authorities and permission authorities
                for (Role role : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getTitle().toUpperCase()));

                    for (Permission permission : role.getPermissions()) {
                        authorities.add(new SimpleGrantedAuthority(permission.getTitle()));
                    }
                }

                // Create an authentication token and set it in the security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,authorities);

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
