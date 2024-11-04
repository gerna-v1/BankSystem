package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.*;
import io.lettuce.core.resource.ClientResources;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final TokenManager tokenManager;
    private final PasswordManager passwordManager;
    private final SessionManager sessionManager;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final UUIDGenerator uuidGenerator;

    @Autowired
    public AuthServiceImpl(TokenManager tokenManager, PasswordManager passwordManager, SessionManager sessionManager, ClientRepository clientRepository, AdminRepository adminRepository, UUIDGenerator uuidGenerator) {
        this.tokenManager = tokenManager;
        this.passwordManager = passwordManager;
        this.sessionManager = sessionManager;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.uuidGenerator = uuidGenerator;
    }

    @Override
    public String loginClient(LoginRequest loginRequest) {

        Optional<ClientEntity> client = clientRepository.findByUsername(loginRequest.getUsername());

        if (client.isPresent() && sessionManager.isSessionActive(client.get().getUuid())) {
            return "https://http.cat/409";
        }

        System.out.println("Intento de login cliente: " + loginRequest.getUsername() + " " + loginRequest.getPassword());

        if (client.isPresent() && passwordManager.checkPassword(loginRequest.getPassword(), client.get().getPassword().getHash(), client.get().getPassword().getSalt())) {
            String token = tokenManager.generateToken(client.get().getUsername(), client.get().getEmail(),
                    "CLIENT", client.get().getId(), 0);

            sessionManager.createSession(client.get().getUuid(), token);
            return token;
        }

        return "https://http.cat/404";
    }

    @Override
    public String loginAdmin(LoginRequest loginRequest) {

        Optional<AdminEntity> admin = adminRepository.findByUsername(loginRequest.getUsername());

        if (admin.isPresent() && sessionManager.isSessionActive(admin.get().getUuid())) {
            return "https://http.cat/409";
        }

        System.out.println("Intento de login admin: " + loginRequest.getUsername() + " " + loginRequest.getPassword());

        if (admin.isPresent() && passwordManager.checkPassword(loginRequest.getPassword(), admin.get().getPassword().getHash(), admin.get().getPassword().getSalt())) {

            String token = tokenManager.generateToken(admin.get().getUsername(), admin.get().getEmail(),
                    "ADMIN", admin.get().getId(), admin.get().getAccessLevel());

            sessionManager.createSession(admin.get().getUuid(), token);
            return token;
        }

        return "https://http.cat/404";
    }

    @Override
    public ClientDTO registerClient(ClientRegisterDTO clientRegisterDTO) {

        String salt = passwordManager.generateSalt();
        PasswordEntity password = PasswordEntity.builder()
                .hash(passwordManager.hashPassword(clientRegisterDTO.getPassword(), salt))
                .salt(salt)
                .build();

        ClientEntity client = ClientEntity.builder()
                .name(clientRegisterDTO.getName())
                .lastName(clientRegisterDTO.getLastName())
                .username(clientRegisterDTO.getUsername())
                .email(clientRegisterDTO.getEmail())
                .password(password)
                .uuid(uuidGenerator.generateUuid(clientRegisterDTO.getEmail()))
                .balance(0)
                .phone(clientRegisterDTO.getPhone())
                .govId(clientRegisterDTO.getGovId())
                .build();

        clientRepository.save(client);

        return ClientDTO.builder()
                .name(client.getName())
                .lastName(client.getLastName())
                .username(client.getUsername())
                .email(client.getEmail())
                .id(client.getId())
                .balance(client.getBalance())
                .build();

    }

    @Override
    public AdminDTO registerAdmin(AdminRegisterDTO adminRegisterDTO) {

        String salt = passwordManager.generateSalt();
        PasswordEntity password = PasswordEntity.builder()
                .hash(passwordManager.hashPassword(adminRegisterDTO.getPassword(), salt))
                .salt(salt)
                .build();

        AdminEntity admin = AdminEntity.builder()
                .name(adminRegisterDTO.getName())
                .lastName(adminRegisterDTO.getLastName())
                .username(adminRegisterDTO.getUsername())
                .email(adminRegisterDTO.getEmail())
                .password(password)
                .uuid(uuidGenerator.generateUuid(adminRegisterDTO.getEmail()))
                .accessLevel(adminRegisterDTO.getAccessLevel())
                .build();

        adminRepository.save(admin);

        return AdminDTO.builder()
                .username(admin.getUsername())
                .email(admin.getEmail())
                .id(admin.getId())
                .accessLevel(admin.getAccessLevel())
                .build();
    }

    public boolean clientExists(String username) {
        return clientRepository.findByUsername(username).isPresent();
    }

    public boolean adminExists(String username) {
        return adminRepository.findByUsername(username).isPresent();
    }
}
