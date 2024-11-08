package com.gerna_v1.banksystem.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenManager {
    public String generateToken(String username, String email, String role, String uuid, int accessLevel);
    boolean validateToken(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String getUsernameFromToken(String token);
    String getEmailFromToken(String token);
    String getRoleFromToken(String token);
    String getUuidFromToken(String token);
    int getAccessLevelFromToken(String token);
    long getExpirationTime();
    String getSecretKey();
}
