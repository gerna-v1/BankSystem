package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<ClientDTO> getClientByUuid(String uuid) {
        return clientRepository.findByUuid(uuid)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<ClientDTO> getClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientDTO> findWorstByBalance() {
        return clientRepository.findTopByOrderByBalanceAsc()
                .map(this::convertToDTO);
    }

    @Override
    public Optional<ClientDTO> findBestByBalance() {
        return clientRepository.findTopByOrderByBalance()
                .map(this::convertToDTO);
    }

    @Override
    public Optional<ClientEntity> findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public Optional<ClientEntity> findByPhoneAndGovId(String phoneNumber, String govID) {
        return clientRepository.findByPhoneAndGovId(phoneNumber, govID);
    }

    @Override
    public Optional<ClientEntity> findByUsernameAndGovId(String username, String govID) {
        return clientRepository.findByUsernameAndGovId(username, govID);
    }

    @Override
    public void save(ClientEntity client) {
        clientRepository.save(client);
    }

    private ClientDTO convertToDTO(ClientEntity entity) {
        return ClientDTO.builder()
                .name(entity.getName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .balance(entity.getBalance())
                .email(entity.getEmail())
                .govId(entity.getGovId())
                .build();
    }
}