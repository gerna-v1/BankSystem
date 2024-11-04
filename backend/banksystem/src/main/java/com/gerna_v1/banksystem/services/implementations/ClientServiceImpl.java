package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.UUIDGenerator;
import com.gerna_v1.banksystem.services.UserMapper;
import com.gerna_v1.banksystem.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService, UUIDGenerator {

    ClientRepository clientRepository;
    UserMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserMapper userMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = userMapper;
    }

    @Override
    public ClientDTO createClient(ClientRegisterDTO clientRegisterDTO) {
        if (clientRegisterDTO == null || clientRegisterDTO.getName() == null || clientRegisterDTO.getEmail() == null) {
            throw new IllegalArgumentException("ClientRegisterDTO and its required fields must not be null");
        }

        if(getClientByUsername(clientRegisterDTO.getUsername()).isPresent() || getClientByEmail(clientRegisterDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Client with this username or email already exists");
        }

        ClientEntity clientEntity = clientMapper.clientRegisterToClientEntity(clientRegisterDTO).builder()
                .balance(0)
                .uuid(generateUuid(clientRegisterDTO.getEmail()))
                .build();
        return clientMapper.clientEntityToClientDTO(clientRepository.save(clientEntity));
    }

    @Override
    public Optional<ClientDTO> getClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .map(clientMapper::clientEntityToClientDTO);
    }

    @Override
    public Optional<ClientDTO> getClientById(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::clientEntityToClientDTO);
    }

    @Override
    public Optional<ClientDTO> getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .stream()
                .findFirst()
                .map(clientMapper::clientEntityToClientDTO);
    }

    @Override
    public String generateUuid(String email) {
        return UUID.nameUUIDFromBytes(email.getBytes()).toString();
    }
}