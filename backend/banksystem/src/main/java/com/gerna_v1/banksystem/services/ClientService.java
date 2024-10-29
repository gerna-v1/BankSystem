package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;

public interface ClientService {
    ClientDTO createClient(ClientDTO client);
}
