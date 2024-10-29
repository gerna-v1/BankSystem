package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.PasswordDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    private final PasswordMapper passwordMapper;

    @Autowired
    public ClientMapper(PasswordMapper passwordMapper) {
        this.passwordMapper = passwordMapper;
    }

    public ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        return ClientDTO.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .lastName(clientEntity.getLastName())
                .email(clientEntity.getEmail())
                .password(passwordMapper.passwordEntityToPasswordDTO(clientEntity.getPassword()))
                .build();
    }

    public ClientEntity clientDTOToClientEntity(ClientDTO clientDTO) {
        return ClientEntity.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .lastName(clientDTO.getLastName())
                .email(clientDTO.getEmail())
                .password(passwordMapper.passwordDTOToPasswordEntity(clientDTO.getPassword()))
                .build();
    }
}
