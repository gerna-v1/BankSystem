package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDTO> getClientByUsername(String username);
    Optional<ClientDTO> getClientByUuid(String uuid);
    List<ClientDTO> getAllClients();
    Optional<ClientDTO> findWorstByBalance();
    Optional<ClientDTO> findBestByBalance();
    Optional<ClientEntity> findByUsername(String username);
    Optional<ClientEntity> findByPhoneAndGovId(String phoneNumber, String govID);
    Optional<ClientEntity> findByUsernameAndGovId(String username, String govID);
    void save(ClientEntity client);
}