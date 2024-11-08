package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Optional<AdminDTO> getAdminByUuid(String uuid) {
        return adminRepository.findById(uuid).map(this::convertToDTO);
    }

    @Override
    public Optional<AdminDTO> getAdminByUsername(String uuid) {
        return adminRepository.findByUsername(uuid).map(this::convertToDTO);
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AdminDTO convertToDTO(AdminEntity adminEntity) {
        return AdminDTO.builder()
                .id(adminEntity.getUuid())
                .username(adminEntity.getUsername())
                .email(adminEntity.getEmail())
                .accessLevel(adminEntity.getAccessLevel())
                .build();
    }
}