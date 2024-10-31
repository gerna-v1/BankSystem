package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.services.ClientMapper;
import com.gerna_v1.banksystem.services.PasswordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements ClientMapper {

    private final PasswordMapper passwordMapper;

    @Autowired
    public ClientMapperImpl(PasswordMapper passwordMapper) {
        this.passwordMapper = passwordMapper;
    }

    @Override
    public ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        return ClientDTO.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .lastName(clientEntity.getLastName())
                .email(clientEntity.getEmail())
                .password(passwordMapper.passwordEntityToPasswordDTO(clientEntity.getPassword()))
                .build();
    }

    @Override
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
