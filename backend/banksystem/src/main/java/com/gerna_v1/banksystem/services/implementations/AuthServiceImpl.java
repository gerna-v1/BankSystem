// src/main/java/com/gerna_v1/banksystem/services/implementations/AuthServiceImpl.java
package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.UserEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.AuthService;
import com.gerna_v1.banksystem.services.SessionManager;
import com.gerna_v1.banksystem.services.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final SessionManager sessionManager;
    private final BCryptPasswordManager passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UUIDGenerator uuidGenerator;
    private final JwtService jwtService;

    @Override
    public TokenResponse registerClient(ClientRegisterDTO clientRegisterDTO) {
        logger.info("Registering client with username: {}", clientRegisterDTO.getUsername());
        if (clientRepository.findByUsername(clientRegisterDTO.getUsername()).isPresent()) {
            logger.error("User with username {} already exists", clientRegisterDTO.getUsername());
            throw new RuntimeException("User with this email already exists");
        }

        var salt = passwordEncoder.generateSalt();

        ClientEntity user = ClientEntity.builder()
                .name(clientRegisterDTO.getName())
                .lastName(clientRegisterDTO.getLastName())
                .govId(clientRegisterDTO.getGovId())
                .phone(clientRegisterDTO.getPhone())
                .balance(0)
                .uuid(uuidGenerator.generateUuid(clientRegisterDTO.getEmail()))
                .username(clientRegisterDTO.getUsername())
                .email(clientRegisterDTO.getEmail())
                .password(
                        passwordEncoder.passwordToEntity(
                                passwordEncoder.hashPassword(clientRegisterDTO.getPassword(), salt)
                                , salt)
                )
                .build();
        UserEntity savedUser = clientRepository.save(user);
        logger.info("Client registered successfully with username: {}", clientRegisterDTO.getUsername());
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionManager.createSession(jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Override
    public TokenResponse registerAdmin(AdminRegisterDTO adminRegisterDTO) {
        logger.info("Registering admin with email: {}", adminRegisterDTO.getEmail());
        if (adminRepository.findByEmail(adminRegisterDTO.getEmail()).isPresent()) {
            logger.error("User with email {} already exists", adminRegisterDTO.getEmail());
            throw new RuntimeException("User with this email already exists");
        }

        var salt = passwordEncoder.generateSalt();

        AdminEntity user = AdminEntity.builder()
                .name(adminRegisterDTO.getName())
                .lastName(adminRegisterDTO.getLastName())
                .accessLevel(adminRegisterDTO.getAccessLevel())
                .uuid(uuidGenerator.generateUuid(adminRegisterDTO.getEmail()))
                .email(adminRegisterDTO.getEmail())
                .password(
                        passwordEncoder.passwordToEntity(
                                passwordEncoder.hashPassword(adminRegisterDTO.getPassword(), salt)
                                , salt)
                )
                .name(adminRegisterDTO.getName())
                .build();
        UserEntity savedUser = adminRepository.save(user);
        logger.info("Admin registered successfully with email: {}", adminRegisterDTO.getEmail());
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionManager.createSession(jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Override
    public TokenResponse loginClient(LoginRequest loginRequest) {
        logger.info("Logging in client with username: {}", loginRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        ClientEntity user = clientRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    logger.error("User with username {} not found", loginRequest.getUsername());
                    return new RuntimeException("User not found");
                });
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionManager.deleteAllSessionsByUserId(user.getUuid());
        sessionManager.createSession(jwtToken);
        logger.info("Client logged in successfully with username: {}", loginRequest.getUsername());
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Override
    public TokenResponse loginAdmin(LoginRequest loginRequest) {
        logger.info("Logging in admin with username: {}", loginRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserEntity user = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    logger.error("User with username {} not found", loginRequest.getUsername());
                    return new RuntimeException("User not found");
                });
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionManager.deleteAllSessionsByUserId(user.getUuid());
        sessionManager.createSession(jwtToken);
        logger.info("Admin logged in successfully with username: {}", loginRequest.getUsername());
        return new TokenResponse(jwtToken, refreshToken);
    }

    public String logout(String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String uuid = jwtService.extractUuid(jwtToken);
        sessionManager.deleteAllSessionsByUserId(uuid);
        SecurityContextHolder.clearContext();
        return jwtService.extractUsername(jwtToken);
    }
}