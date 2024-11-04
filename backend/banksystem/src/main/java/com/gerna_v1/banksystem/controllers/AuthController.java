package com.gerna_v1.banksystem.controllers;

import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.services.TokenManager;
import com.gerna_v1.banksystem.services.implementations.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final TokenManager tokenManager;

    private final AuthServiceImpl authenticationService;

    public AuthController(TokenManager tokenManager, AuthServiceImpl authenticationService) {
        this.tokenManager = tokenManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup/client")
    public ResponseEntity<ClientDTO> register(@RequestBody ClientRegisterDTO clientRegisterDTO) {
        ClientDTO registeredUser = authenticationService.registerClient(clientRegisterDTO);
        System.out.println("Usuario registrado: " + registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        try {
            AdminDTO registeredAdmin = authenticationService.registerAdmin(adminRegisterDTO);

            System.out.println("Admin registrado: " + registeredAdmin);

            return ResponseEntity.ok(registeredAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login/client")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            String authenticatedUser = authenticationService.loginClient(loginRequest);

            if (authenticatedUser.equals("https://http.cat/404")) {
                String code = authenticatedUser.substring(authenticatedUser.lastIndexOf("/") + 1);
                return ResponseEntity.badRequest().body(authenticatedUser);
            }

            return ResponseEntity.ok(authenticatedUser);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("No client found");
        }
    }

    @PostMapping("/login/admin")
    public ResponseEntity<String> authenticateAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            String authenticatedAdmin = authenticationService.loginAdmin(loginRequest);

            if (authenticatedAdmin.equals("https://http.cat/404")) {
                return ResponseEntity.badRequest().body(authenticatedAdmin);
            }

            return ResponseEntity.ok(authenticatedAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No admin found");
        }
    }
}