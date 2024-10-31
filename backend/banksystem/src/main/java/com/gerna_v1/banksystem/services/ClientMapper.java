package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.PasswordDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.UserEntity;

public interface ClientMapper {

    ClientDTO clientEntityToClientDTO(ClientEntity clientEntity);

    ClientEntity clientDTOToClientEntity(ClientDTO clientDTO);
}