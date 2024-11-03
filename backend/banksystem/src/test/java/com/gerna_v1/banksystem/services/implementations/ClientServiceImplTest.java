package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserMapperImpl clientMapper;

    @InjectMocks
    private ClientServiceImpl underTest;

    @Test
    public void testCreateClient() {
        ClientRegisterDTO clientRegister = TestData.testClientRegister();
        ClientEntity clientEntity = TestData.testClientEntity();
        ClientDTO clientDTO = TestData.testClient();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.clientRegisterToClientEntity(any(ClientRegisterDTO.class))).thenReturn(clientEntity);
        when(clientMapper.clientEntityToClientDTO(any(ClientEntity.class))).thenReturn(clientDTO);

        ClientDTO result = underTest.createClient(clientRegister);

        assertEquals(clientDTO, result);
    }
}