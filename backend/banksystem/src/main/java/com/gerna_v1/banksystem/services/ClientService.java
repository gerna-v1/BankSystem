package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;

import java.util.Optional;

public interface ClientService {
    ClientDTO createClient(ClientRegisterDTO clientRegisterDTO);
    Optional<ClientDTO> getClientById(String id);
}