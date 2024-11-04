package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.*;

public interface AuthService {
    String loginClient(LoginRequest loginRequest);
    String loginAdmin(LoginRequest loginRequest);
    ClientDTO registerClient(ClientRegisterDTO clientRegisterDTO);
    AdminDTO registerAdmin(AdminRegisterDTO adminRegisterDTO);
}
