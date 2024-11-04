package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenManager tokenManager;
    private final PasswordManager passwordManager;
    private final ClientService clientService;
    private final AdminService adminService;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public AuthServiceImpl(TokenManagerImpl tokenManager, BCryptPasswordManager passwordManager, AdminServiceImpl adminService, ClientServiceImpl clientService, ClientRepository clientRepository, AdminRepository adminRepository) {
        this.tokenManager = tokenManager;
        this.passwordManager = passwordManager;
        this.clientService = clientService;
        this.adminService = adminService;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public String registerClient(ClientRegisterDTO clientRegister) {
        if (clientService.getClientByUsername(clientRegister.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (clientService.getClientByEmail(clientRegister.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        ClientDTO client = clientService.createClient(clientRegister);

        return tokenManager.generateToken(
                client.getId(),
                client.getUsername(),
                "CLIENT",
                client.getId(),
                0
        );
    }

    @Override
    public String registerAdmin(AdminRegisterDTO adminRegister) {
        if (adminService.getAdminByUsername(adminRegister.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (adminService.getAdminByEmail(adminRegister.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        AdminDTO admin = adminService.createAdmin(adminRegister);

        return tokenManager.generateToken(
                admin.getId(),
                admin.getUsername(),
                "ADMIN",
                admin.getId(),
                0
        );
    }

    @Override
    public String loginAdmin(LoginRequest loginRequest) {
        AdminDTO admin = adminService.getAdminByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        AdminEntity adminInDB = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!passwordManager.checkPassword(loginRequest.getPassword(),
                adminInDB.getPassword().getHash(), adminInDB.getPassword().getSalt())) {
            throw new RuntimeException("Invalid password");
        }

        return tokenManager.generateToken(
                admin.getId(),
                admin.getUsername(),
                "ADMIN",
                admin.getId(),
                0
        );
    }

    @Override
    public String loginClient(LoginRequest loginRequest) {
        ClientDTO client = clientService.getClientByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        ClientEntity clientInDB = clientRepository.findById(client.getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!passwordManager.checkPassword(loginRequest.getPassword(),
                clientInDB.getPassword().getHash(), clientInDB.getPassword().getSalt())) {
            throw new RuntimeException("Invalid password");
        }

        return tokenManager.generateToken(
                client.getId(),
                client.getUsername(),
                "CLIENT",
                client.getId(),
                0
        );
    }
}
