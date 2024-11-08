package com.gerna_v1.banksystem.controllers;

import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/register/client")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TokenResponse>> registerClient(@Valid @RequestBody ClientRegisterDTO clientRegisterDTO) {
        logger.info("Received request to register client with username: {}", clientRegisterDTO.getUsername());
        try {
            TokenResponse response = authService.registerClient(clientRegisterDTO);
            logger.info("Client registered successfully with username: {}", clientRegisterDTO.getUsername());
            return ResponseEntity.ok(ApiResponse.<TokenResponse>builder()
                    .success(true)
                    .data(response)
                    .message("User registered successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error registering client with username: {}", clientRegisterDTO.getUsername(), e);
            return ResponseEntity.badRequest().body(ApiResponse.<TokenResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TokenResponse>> registerAdmin(@Valid @RequestBody AdminRegisterDTO adminRegisterDTO) {
        logger.info("Received request to register admin with email: {}", adminRegisterDTO.getEmail());
        try {
            TokenResponse response = authService.registerAdmin(adminRegisterDTO);
            logger.info("Admin registered successfully with email: {}", adminRegisterDTO.getEmail());
            return ResponseEntity.ok(ApiResponse.<TokenResponse>builder()
                    .success(true)
                    .data(response)
                    .message("Admin registered successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error registering admin with email: {}", adminRegisterDTO.getEmail(), e);
            return ResponseEntity.badRequest().body(ApiResponse.<TokenResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/login/client")
    public ResponseEntity<ApiResponse<TokenResponse>> loginClient(@RequestBody LoginRequest loginRequest) {
        logger.info("Received request to login client with username: {}", loginRequest.getUsername());
        try {
            TokenResponse response = authService.loginClient(loginRequest);
            logger.info("Client logged in successfully with username: {}", loginRequest.getUsername());
            return ResponseEntity.ok(ApiResponse.<TokenResponse>builder()
                    .success(true)
                    .data(response)
                    .message("Client logged in successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error logging in client with username: {}", loginRequest.getUsername(), e);
            return ResponseEntity.badRequest().body(ApiResponse.<TokenResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/login/admin")
    public ResponseEntity<ApiResponse<TokenResponse>> loginAdmin(@RequestBody LoginRequest loginRequest) {
        logger.info("Received request to login admin with username: {}", loginRequest.getUsername());
        try {
            TokenResponse response = authService.loginAdmin(loginRequest);
            logger.info("Admin logged in successfully with username: {}", loginRequest.getUsername());
            return ResponseEntity.ok(ApiResponse.<TokenResponse>builder()
                    .success(true)
                    .data(response)
                    .message("Admin logged in successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error logging in admin with username: {}", loginRequest.getUsername(), e);
            return ResponseEntity.badRequest().body(ApiResponse.<TokenResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            authService.logout(token);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .success(true)
                    .message("All sessions cleared successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(401).body(ApiResponse.<String>builder()
                    .success(false)
                    .message("Unauthorized")
                    .build());
        }
    }
}