package com.unibook.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Value("${frontend.port}")
    private String frontendPort;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow Frontend to access Backend
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);               
            }
        };
    }
}