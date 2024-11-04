package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.LoginRequest;

public interface AuthService {
    String loginClient(LoginRequest loginRequest);
    String loginAdmin(LoginRequest loginRequest);
    String registerClient(ClientRegisterDTO clientRegisterDTO);
    String registerAdmin(AdminRegisterDTO adminRegisterDTO);
}
