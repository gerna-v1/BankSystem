package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.UserMapper;
import com.gerna_v1.banksystem.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

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
        return clientMapper.clientEntityToClientDTO(
                clientRepository.save(
                        clientMapper.clientRegisterToClientEntity(clientRegisterDTO)
                )
        );
    }

    @Override
    public Optional<ClientDTO> getClientById(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::clientEntityToClientDTO);
    }
}