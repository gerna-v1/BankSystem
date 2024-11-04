package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.services.TokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenManagerImpl implements TokenManager {

    private final Key secretKey;
    private static final String SECRET_KEY = "aKeyThatNoOneHasAccessTo";
    private static final long EXPIRATION_TIME = 3600000; // Each token will last an hour

    public TokenManagerImpl() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()).getBytes());
    }

    @Override
    public String generateToken(String username, String email, String role, String id, int accessLevel) {
        return Jwts.builder()
                .setSubject(username)
                .claim("email", email)
                .claim("id", id)
                .claim("role", role)
                .claim("accessLevel", accessLevel)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && validateToken(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @Override
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("email", String.class);
    }

    @Override
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    @Override
    public int getAccessLevelFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("accessLevel", Integer.class);
    }

    @Override
    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }
}