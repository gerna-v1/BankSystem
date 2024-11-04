package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl underTest;

    @Test
    public void testCreateClient() {
        ClientRegisterDTO clientRegister = TestData.testClientRegister();
        ClientEntity clientEntity = TestData.testClientEntity();
        ClientDTO clientDTO = TestData.testClientDTO();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.clientRegisterToClientEntity(any(ClientRegisterDTO.class))).thenReturn(clientEntity);
        when(clientMapper.clientEntityToClientDTO(any(ClientEntity.class))).thenReturn(clientDTO);

        ClientDTO result = underTest.createClient(clientRegister);

        assertEquals(clientDTO, result);
    }

    @Test
    public void testGetClientByUsername() {
        String username = "testUsername";
        ClientEntity clientEntity = TestData.testClientEntity();
        ClientDTO clientDTO = TestData.testClientDTO();

        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(clientEntity));
        when(clientMapper.clientEntityToClientDTO(clientEntity)).thenReturn(clientDTO);

        Optional<ClientDTO> result = underTest.getClientByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(clientDTO, result.get());
    }

    @Test
    public void testGetClientById() {
        String id = "testId";
        ClientEntity clientEntity = TestData.testClientEntity();
        ClientDTO clientDTO = TestData.testClientDTO();

        when(clientRepository.findById(id)).thenReturn(Optional.of(clientEntity));
        when(clientMapper.clientEntityToClientDTO(clientEntity)).thenReturn(clientDTO);

        Optional<ClientDTO> result = underTest.getClientById(id);

        assertTrue(result.isPresent());
        assertEquals(clientDTO, result.get());
    }

    @Test
    public void testGetClientByEmail() {
        ClientEntity clientEntity = TestData.testClientEntity();
        String email = clientEntity.getEmail();
        ClientDTO clientDTO = TestData.testClientDTO();

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(clientEntity));
        when(clientMapper.clientEntityToClientDTO(clientEntity)).thenReturn(clientDTO);

        Optional<ClientDTO> result = underTest.getClientByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(clientDTO, result.get());
    }
}