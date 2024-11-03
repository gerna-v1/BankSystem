package com.gerna_v1.banksystem.services;

public interface TokenManager {
    public String generateToken(String username, String email, String role, String id, int accessLevel);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    String getEmailFromToken(String token);
    String getRoleFromToken(String token);
}
