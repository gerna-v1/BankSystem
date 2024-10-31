package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.LoginDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String registerClient(ClientRegisterDTO clientRegisterDTO);
    String registerAdmin(AdminRegisterDTO adminRegisterDTO);
}
