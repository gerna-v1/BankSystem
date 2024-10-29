package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.ClientMapper;
import com.gerna_v1.banksystem.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;
    ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }


    @Override
    public ClientDTO createClient(ClientDTO client) {
        return clientMapper.clientEntityToClientDTO(
                clientRepository.saveClient(
                        clientMapper.clientDTOToClientEntity(client)
                )
        );
    }
}
