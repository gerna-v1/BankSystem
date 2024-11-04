package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceImplTest {

    @Mock
    private TokenManager tokenManager;

    @Mock
    private PasswordManager passwordManager;

    @Mock
    private SessionManager sessionManager;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UUIDGenerator uuidGenerator;

    @InjectMocks
    private AuthServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginClient_Success() {
        LoginRequest loginRequest = TestData.testLoginRequest();
        ClientEntity clientEntity = TestData.testClientEntity();
        PasswordEntity passwordEntity = TestData.testPasswordEntity();
        clientEntity.setPassword(passwordEntity);

        when(clientRepository.findByUsername(clientEntity.getUsername())).thenReturn(Optional.of(clientEntity));
        when(passwordManager.checkPassword(anyString(), anyString(), anyString())).thenReturn(true);
        when(tokenManager.generateToken(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn("token");

        String result = underTest.loginClient(loginRequest);
        assertEquals("token", result);
    }

    @Test
    public void testLoginClient_Failure() {
        LoginRequest loginRequest = new LoginRequest("clientUsername", "clientPassword");

        when(clientRepository.findByUsername("clientUsername")).thenReturn(Optional.empty());

        String result = underTest.loginClient(loginRequest);
        assertEquals("No client found", result);
    }

    @Test
    public void testRegisterClient() {
        ClientRegisterDTO clientRegisterDTO = TestData.testClientRegister();
        PasswordEntity passwordEntity = TestData.testPasswordEntity();
        ClientEntity clientEntity = TestData.testClientEntity();
        clientEntity.setPassword(passwordEntity);

        when(passwordManager.generateSalt()).thenReturn("salt");
        when(passwordManager.hashPassword(anyString(), anyString())).thenReturn("hashedPassword");
        when(uuidGenerator.generateUuid(anyString())).thenReturn("uuid");
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientDTO result = underTest.registerClient(clientRegisterDTO);
        assertEquals("johndoe", result.getUsername());
        assertEquals("email", result.getEmail());
    }

    // Similar tests can be written for loginAdmin and registerAdmin methods
}
