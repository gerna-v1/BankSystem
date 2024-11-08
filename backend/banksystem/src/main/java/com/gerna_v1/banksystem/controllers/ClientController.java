package com.gerna_v1.banksystem.controllers;

import com.gerna_v1.banksystem.models.DTOs.ApiResponse;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.services.ClientService;
import com.gerna_v1.banksystem.services.implementations.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final JwtService jwtService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ClientDTO>> getClientInfo(@RequestHeader("Authorization") String token) {
        try {
            String uuid = jwtService.extractUuid(token.substring(7)); // Remove "Bearer " prefix
            Optional<ClientDTO> clientDTO = clientService.getClientByUuid(uuid);
            if (clientDTO.isPresent()) {
                return ResponseEntity.ok(ApiResponse.<ClientDTO>builder()
                        .success(true)
                        .data(clientDTO.get())
                        .message("Client information retrieved successfully")
                        .build());
            } else {
                return ResponseEntity.status(404).body(ApiResponse.<ClientDTO>builder()
                        .success(false)
                        .message("Client not found")
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(ApiResponse.<ClientDTO>builder()
                    .success(false)
                    .message("Unauthorized")
                    .build());
        }
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClientDTO>> getClientByUsername(String username) {
        Optional<ClientDTO> clientDTO = clientService.getClientByUsername(username);
        if (clientDTO.isPresent()) {
            return ResponseEntity.ok(ApiResponse.<ClientDTO>builder()
                    .success(true)
                    .data(clientDTO.get())
                    .message("Client information retrieved successfully")
                    .build());
        } else {
            return ResponseEntity.status(404).body(ApiResponse.<ClientDTO>builder()
                    .success(false)
                    .message("Client not found")
                    .build());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(ApiResponse.<List<ClientDTO>>builder()
                .success(true)
                .data(clients)
                .message("All clients retrieved successfully")
                .build());
    }
}