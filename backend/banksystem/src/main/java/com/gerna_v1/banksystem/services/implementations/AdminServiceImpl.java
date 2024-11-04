package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.services.AdminService;
import com.gerna_v1.banksystem.services.UUIDGenerator;
import com.gerna_v1.banksystem.services.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService, UUIDGenerator {

    private final AdminRepository adminRepository;
    private final UserMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, UserMapperImpl adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public AdminDTO createAdmin(AdminRegisterDTO adminRegisterDTO) {
        if (adminRegisterDTO == null || adminRegisterDTO.getUsername() == null || adminRegisterDTO.getEmail() == null) {
            throw new IllegalArgumentException("AdminRegisterDTO and its required fields must not be null");
        }

        String uuid = generateUuid(adminRegisterDTO.getEmail());

        AdminEntity adminEntity = adminMapper.adminRegisterToAdminEntity(adminRegisterDTO).builder()
                .accessLevel(0)
                .uuid(uuid)
                .build();
        AdminEntity savedEntity = adminRepository.save(adminEntity);
        AdminDTO adminDTO = adminMapper.adminEntityToAdminDTO(savedEntity);
        return adminDTO;
    }

    @Override
    public Optional<AdminDTO> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .map(adminMapper::adminEntityToAdminDTO);
    }

    @Override
    public Optional<AdminDTO> getAdminById(String id) {
        return adminRepository.findById(id)
                .map(adminMapper::adminEntityToAdminDTO);
    }

    @Override
    public Optional<AdminDTO> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .stream()
                .findFirst()
                .map(adminMapper::adminEntityToAdminDTO);
    }

    @Override
    public List<AdminDTO> getAdminsByAccessLevel(int accessLevel) {
        return adminRepository.getAdminEntitiesByAccessLevel(accessLevel)
                .stream()
                .map(adminMapper::adminEntityToAdminDTO)
                .toList();
    }

    @Override
    public String generateUuid(String email) {
        return UUID.nameUUIDFromBytes(email.getBytes()).toString();
    }
}