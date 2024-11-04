package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.services.UserMapper;
import com.gerna_v1.banksystem.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordManager passwordManager;

    @Autowired
    public UserMapperImpl(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Override
    public ClientEntity clientRegisterToClientEntity(ClientRegisterDTO clientRegister) {
        return ClientEntity.builder()
                .name(clientRegister.getName())
                .lastName(clientRegister.getLastName())
                .username(clientRegister.getUsername())
                .email(clientRegister.getEmail())
                .password(
                        passwordManager.passwordToEntity(
                                clientRegister.getPassword(),
                                passwordManager.generateSalt()
                        )
                )
                .balance(clientRegister.getBalance())
                .phone(clientRegister.getPhone())
                .govId(clientRegister.getGovId())
                .build();
    }

    @Override
    public AdminEntity adminRegisterToAdminEntity(AdminRegisterDTO adminRegister) {
        return AdminEntity.builder()
                .name(adminRegister.getName())
                .lastName(adminRegister.getLastName())
                .username(adminRegister.getUsername())
                .email(adminRegister.getEmail())
                .password(
                        passwordManager.passwordToEntity(
                                adminRegister.getPassword(),
                                passwordManager.generateSalt()
                        )
                )
                .build();
    }

    @Override
    public ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        return ClientDTO.builder()
                .name(clientEntity.getName())
                .lastName(clientEntity.getLastName())
                .username(clientEntity.getUsername())
                .email(clientEntity.getEmail())
                .id(clientEntity.getId())
                .balance(clientEntity.getBalance())
                .build();
    }

    @Override
    public AdminDTO adminEntityToAdminDTO(AdminEntity adminEntity) {
        return AdminDTO.builder()
                .username(adminEntity.getUsername())
                .email(adminEntity.getEmail())
                .id(adminEntity.getId())
                .build();
    }
}