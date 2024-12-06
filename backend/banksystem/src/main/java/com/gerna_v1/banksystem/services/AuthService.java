package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.*;

public interface AuthService {
    TokenResponse registerClient(ClientRegisterDTO clientRegisterDTO);
    TokenResponse registerAdmin(AdminRegisterDTO adminRegisterDTO);
    TokenResponse loginClient(LoginRequest loginRequest);
    TokenResponse loginAdmin(LoginRequest loginRequest);
    String logout(String token);
}