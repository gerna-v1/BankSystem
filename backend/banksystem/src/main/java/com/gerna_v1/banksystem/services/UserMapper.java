package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;

public interface UserMapper {
    ClientEntity clientRegisterToClientEntity(ClientRegisterDTO clientRegister);
    AdminEntity adminRegisterToAdminEntity(AdminRegisterDTO adminRegister);
    ClientDTO clientEntityToClientDTO(ClientEntity clientEntity);
    AdminDTO adminEntityToAdminDTO(AdminEntity adminEntity);
}