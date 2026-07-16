package com.unibook.app.filter;

import tools.jackson.databind.ObjectMapper;
import com.unibook.app.model.Permission;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.UserRepository;
import com.unibook.app.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader =
            request.getHeader("Authorization");

        if (
            authHeader == null ||
            !authHeader.startsWith("Bearer ")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7).trim();

        if (token.isBlank()) {
            writeUnauthorizedResponse(
                response,
                request,
                "Token missing",
                "TOKEN_MISSING"
            );

            return;
        }

        try {
            Claims claims =
                jwtService.extractAllClaims(token);

            String username = claims.getSubject();

            if (
                username != null &&
                SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null
            ) {
                authenticateUser(
                    claims,
                    username
                );
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException exception) {
            SecurityContextHolder.clearContext();

            writeUnauthorizedResponse(
                response,
                request,
                "Token expired",
                "TOKEN_EXPIRED"
            );

        } catch (JwtException | IllegalArgumentException exception) {
            SecurityContextHolder.clearContext();

            writeUnauthorizedResponse(
                response,
                request,
                "Invalid token",
                "INVALID_TOKEN"
            );
        }
    }

    private void authenticateUser(
        Claims claims,
        String username
    ) {
        User user = userRepository
            .findByLoginWithRoles(username)
            .orElse(null);

        if (
            user == null ||
            !jwtService.isValid(claims, user)
        ) {
            return;
        }

        var authorities =
            new ArrayList<SimpleGrantedAuthority>();

        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            authorities.add(
                new SimpleGrantedAuthority(
                    "ROLE_" +
                    role.getTitle().toUpperCase()
                )
            );

            for (
                Permission permission :
                role.getPermissions()
            ) {
                authorities.add(
                    new SimpleGrantedAuthority(
                        permission.getTitle()
                    )
                );
            }
        }

        var authentication =
            new UsernamePasswordAuthenticationToken(
                user,
                null,
                authorities
            );

        SecurityContextHolder
            .getContext()
            .setAuthentication(authentication);
    }

    private void writeUnauthorizedResponse(
        HttpServletResponse response,
        HttpServletRequest request,
        String message,
        String code
    ) throws IOException {

        if (response.isCommitted()) {
            return;
        }

        response.setStatus(
            HttpServletResponse.SC_UNAUTHORIZED
        );

        response.setContentType(
            MediaType.APPLICATION_JSON_VALUE
        );

        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body =
            new LinkedHashMap<>();

        body.put(
            "timestamp",
            Instant.now().toString()
        );

        body.put(
            "status",
            HttpServletResponse.SC_UNAUTHORIZED
        );

        body.put(
            "error",
            "Unauthorized"
        );

        body.put(
            "message",
            message
        );

        body.put(
            "code",
            code
        );

        body.put(
            "path",
            request.getRequestURI()
        );

        objectMapper.writeValue(
            response.getOutputStream(),
            body
        );
    }
}