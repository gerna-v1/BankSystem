package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    AdminDTO createAdmin(AdminRegisterDTO adminRegisterDTO);
    Optional<AdminDTO> getAdminByUsername(String username);
    Optional<AdminDTO> getAdminById(String id);
    Optional<AdminDTO> getAdminByEmail(String email);
    List<AdminDTO> getAdminsByAccessLevel(int accessLevel);
}
