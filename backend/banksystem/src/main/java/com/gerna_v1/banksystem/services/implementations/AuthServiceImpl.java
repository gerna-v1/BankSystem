package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.LoginRequest;
import com.gerna_v1.banksystem.services.AuthService;
import com.gerna_v1.banksystem.services.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenManager tokenManager;


    /**
     * @param loginRequest
     * @return
     */
    @Override
    public String login(LoginRequest loginRequest) {
        return "";
    }

    /**
     * @param clientRegisterDTO
     * @return
     */
    @Override
    public String registerClient(ClientRegisterDTO clientRegisterDTO) {
        return "";
    }

    /**
     * @param adminRegisterDTO
     * @return
     */
    @Override
    public String registerAdmin(AdminRegisterDTO adminRegisterDTO) {
        return "";
    }
}
