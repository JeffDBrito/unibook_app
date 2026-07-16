package com.unibook.app.service;

import java.nio.charset.StandardCharsets;
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
     * Generates a JWT token for the authenticated user.
     */
    public String generateToken(User user) {
        long expirationInMilliseconds =
            1000L * 60 * 60 * expirationTime;

        Date issuedAt = new Date();

        Date expiration = new Date(
            issuedAt.getTime() + expirationInMilliseconds
        );

        return Jwts.builder()
            .setSubject(user.getLogin())
            .claim(
                "roles",
                user.getRoles()
                    .stream()
                    .map(role -> role.getTitle())
                    .toList()
            )
            .claim("id", user.getId())
            .claim("name", user.getPerson().getName())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(
                getSignKey(),
                SignatureAlgorithm.HS256
            )
            .compact();
    }

    /**
     * Extracts all claims and validates the token signature
     * and expiration date.
     *
     * Expired or invalid tokens cause a JwtException.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Checks whether token claims belong to the given user.
     *
     * The expiration is already validated when extracting
     * the claims, but it is checked again for clarity.
     */
    public boolean isValid(Claims claims, User user) {
        if (claims == null || user == null) {
            return false;
        }

        String username = claims.getSubject();
        Date expiration = claims.getExpiration();

        return username != null
            && username.equals(user.getLogin())
            && expiration != null
            && expiration.after(new Date());
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(
            secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }
}