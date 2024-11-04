package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import com.gerna_v1.banksystem.services.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserMapperImplTest {

    @Mock
    private PasswordManager passwordManager;

    @InjectMocks
    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testClientRegisterToClientEntity() {
        ClientRegisterDTO clientRegisterDTO = TestData.testClientRegister();

        PasswordEntity password = TestData.testPasswordEntity();

        when(passwordManager.passwordToEntity(anyString(), anyString())).thenReturn(password);
        when(passwordManager.generateSalt()).thenReturn("salt");

        ClientEntity clientEntity = userMapper.clientRegisterToClientEntity(clientRegisterDTO);

        assertEquals(clientRegisterDTO.getName(), clientEntity.getName());
        assertEquals(clientRegisterDTO.getLastName(), clientEntity.getLastName());
        assertEquals(clientRegisterDTO.getUsername(), clientEntity.getUsername());
        assertEquals(clientRegisterDTO.getEmail(), clientEntity.getEmail());
        assertEquals(password.getHash(), clientEntity.getPassword().getHash());
        assertEquals(clientRegisterDTO.getBalance(), clientEntity.getBalance());
        assertEquals(clientRegisterDTO.getPhone(), clientEntity.getPhone());
        assertEquals(clientRegisterDTO.getGovId(), clientEntity.getGovId());
    }

    @Test
    void testAdminRegisterToAdminEntity() {
        AdminRegisterDTO adminRegisterDTO = TestData.testAdminRegister();

        PasswordEntity password = TestData.testPasswordEntity();

        when(passwordManager.passwordToEntity(anyString(), anyString())).thenReturn(password);
        when(passwordManager.generateSalt()).thenReturn("salt");

        AdminEntity adminEntity = userMapper.adminRegisterToAdminEntity(adminRegisterDTO);

        assertEquals(adminRegisterDTO.getName(), adminEntity.getName());
        assertEquals(adminRegisterDTO.getLastName(), adminEntity.getLastName());
        assertEquals(adminRegisterDTO.getUsername(), adminEntity.getUsername());
        assertEquals(adminRegisterDTO.getEmail(), adminEntity.getEmail());
        assertEquals(password.getHash(), adminEntity.getPassword().getHash());
    }

    @Test
    void testClientEntityToClientDTO() {
        ClientEntity clientEntity = TestData.testClientEntity();

        ClientDTO clientDTO = userMapper.clientEntityToClientDTO(clientEntity);

        assertEquals(clientEntity.getName(), clientDTO.getName());
        assertEquals(clientEntity.getLastName(), clientDTO.getLastName());
        assertEquals(clientEntity.getUsername(), clientDTO.getUsername());
        assertEquals(clientEntity.getEmail(), clientDTO.getEmail());
        assertEquals(clientEntity.getId(), clientDTO.getId());
        assertEquals(clientEntity.getBalance(), clientDTO.getBalance());
    }

    @Test
    void testAdminEntityToAdminDTO() {
        AdminEntity adminEntity = TestData.testAdminEntity();

        AdminDTO adminDTO = userMapper.adminEntityToAdminDTO(adminEntity);

        assertEquals(adminEntity.getUsername(), adminDTO.getUsername());
        assertEquals(adminEntity.getEmail(), adminDTO.getEmail());
        assertEquals(adminEntity.getId(), adminDTO.getId());
    }
}