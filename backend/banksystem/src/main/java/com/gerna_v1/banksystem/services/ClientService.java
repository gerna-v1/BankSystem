package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDTO> getClientByUsername(String username);
    Optional<ClientDTO> getClientByUuid(String uuid);
    List<ClientDTO> getAllClients();
}