package com.unibook.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unibook.app.exceptions.CustomAuthEntryPoint;
import com.unibook.app.filter.JwtFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final CustomAuthEntryPoint
        customAuthEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(
        HttpSecurity http
    ) throws Exception {

        http
            .cors(Customizer.withDefaults())

            .csrf(csrf ->
                csrf.disable()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                    customAuthEntryPoint
                )
            )

            .authorizeHttpRequests(authorize ->
                authorize
                    .requestMatchers(
                        "/auth/login",
                        "/auth/signup",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/error"
                    )
                    .permitAll()

                    .anyRequest()
                    .authenticated()
            )

            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}