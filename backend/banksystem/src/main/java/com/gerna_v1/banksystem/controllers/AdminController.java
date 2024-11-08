package com.gerna_v1.banksystem.controllers;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.ApiResponse;
import com.gerna_v1.banksystem.services.AdminService;
import com.gerna_v1.banksystem.services.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SessionManager sessionManager;
    private final AdminService adminService;

    @DeleteMapping("/clear-sessions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> clearAllSessions() {
        sessionManager.deleteAllSessions();
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("All sessions cleared successfully")
                .build());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminDTO>> getAdminByUsername(@PathVariable String username) {
        return adminService.getAdminByUsername(username)
                .map(admin -> ResponseEntity.ok(ApiResponse.<AdminDTO>builder()
                        .success(true)
                        .data(admin)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.<AdminDTO>builder()
                        .success(false)
                        .message("Admin not found")
                        .build()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdminDTO>>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(ApiResponse.<List<AdminDTO>>builder()
                .success(true)
                .message("All admins retrieved successfully")
                .data(admins)
                .build());
    }
}