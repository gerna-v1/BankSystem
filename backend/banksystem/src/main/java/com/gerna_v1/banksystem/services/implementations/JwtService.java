package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final String secretKey
            = "c723a9aaac3ef8cfbe2a8564ac0aef41b9e1f68426a4d6ca06b60897ea016404bef9db62af7a237f165a7d33ca3417f8b0ee3bfce140b4e5dedcf14e6a140047c13455034c133eccb433c6b309436c2107aa7a8503ada71de81e361a4464635d324b2786bd25549a1fdea9a282d1fa2809bcfdfece64b95b9530736f454d4993176de63f5da52c211f79ee250480e26f3b740948b1cf4ba9c3e537de543dacf92df18e2b853e9176bb3fba649236fb411f9f35f50101901e774d9a6c523594865f4b1175a6cf45cf452de159aa5f54359a163f823056c02eb92360101cf37835775908441fffeed6cf8e6e369ed2f01b57b5df563d734635faa53c45eeb3d0b5";
    private final long jwtExpiration = 86400000; // One day
    private final long refreshExpiration = 604800000; // One week
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public String generateToken(UserEntity user) {
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(UserEntity user) {
        return buildToken(user, refreshExpiration);
    }

    public String extractUsername(String token) {
        try {
            Claims jwtToken = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return jwtToken.getSubject();
        } catch (SignatureException e) {
            logger.warn("JWT signature does not match locally computed signature", e);
            return null;
        }
    }

    public String extractUuid(String token) {
        try {
            Claims jwtToken = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return jwtToken.get("uuid", String.class);
        } catch (SignatureException e) {
            logger.warn("JWT signature does not match locally computed signature", e);
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("role", String.class);
        } catch (SignatureException e) {
            logger.warn("JWT signature does not match locally computed signature", e);
            return null;
        }
    }

    public boolean isTokenValid(String token, UserEntity user) {
        String email = extractUsername(token);
        return (email != null && email.equals(user.getUsername()) && !isTokenExpired(token) && isSignatureValid(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isSignatureValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Date extractExpiration(String token) {
        Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return jwtToken.getExpiration();
    }

    private String buildToken(UserEntity user, long expiration) {
        String role = user instanceof AdminEntity ? "ADMIN" : "CLIENT";
        return Jwts.builder()
                .setClaims(Map.of(
                        "uuid", user.getUuid(),
                        "role", role
                ))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}