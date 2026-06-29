package com.npopov.philharmonic.shared.security;

import com.npopov.philharmonic.identity.role.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private Key key() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username, Set<Role> roles) {
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Set<String> extractRoleNames(String token) {
        Claims claims = extractClaims(token);
        return new HashSet<>((List<String>) claims.get("roles"));
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}