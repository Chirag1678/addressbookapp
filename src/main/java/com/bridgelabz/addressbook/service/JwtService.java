package com.bridgelabz.addressbook.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private Key getSigningKey() {
        // Secret key
        String key = "address_book_bridgelabz_project_to_enable_jwt_authentication";
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    // method to generate jwt token
    public String generateToken(Long personId, String userName) {
        return Jwts.builder()
                .claim("personId", personId)
                .claim("userName", userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1-day expiry
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // method to get user Id from token
    public Long extractUserId(String token) {
        Claims claims = parseToken(token);
        return claims.get("personId", Long.class);
    }

    // method to get username from token
    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        return claims.get("userName", String.class);
    }

    // method to parse token
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validToken(String token, UserDetails user) {
        final String userName = extractUsername(token);
        return (userName.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }
}
