package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Optional<AdminDTO> getAdminByUsername(String username);
    Optional<AdminDTO> getAdminByUuid(String uuid);
    List<AdminDTO> getAllAdmins();
}