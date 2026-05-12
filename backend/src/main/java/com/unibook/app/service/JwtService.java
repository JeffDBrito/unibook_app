package com.unibook.app.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.unibook.app.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expirationTime;

    /**
     * Generate Token
     * @param user
     * @return String
     */
    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getLogin())
            .claim("roles", user.getRoles().stream().map(r -> r.getTitle()).toList())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expirationTime)) // 1h ttl
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Extract Username by token
     * @param token
     * @return String
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Check if token is valid
     * @param token
     * @param user
     * @return boolean
     */
    public boolean isValid(String token, User user) {
        String username = extractUsername(token);
        return username.equals(user.getLogin()) && !isTokenExpired(token);
    }

    /**
     * Check if token is expired
     * @param token
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extract all claims
     * @param token
     * @return Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Get Sign Key
     * @return Key
     */
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}