package com.gerna_v1.banksystem.controllers;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.ClientRegisterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /* @MockBean
    private ClientService clientService;

    @Test
    public void testCreateClient() throws Exception {
        ClientDTO clientDTO = TestData.testClientDTO();
        ClientRegisterDTO clientRegisterDTO = TestData.testClientRegister();

        when(clientService.createClient(any(ClientRegisterDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + clientRegisterDTO.getName() + "\",\"lastName\":\"" + clientRegisterDTO.getLastName() + "\",\"username\":\"" + clientRegisterDTO.getUsername()
                                + "\",\"email\":\"" + clientRegisterDTO.getEmail()
                                + "\",\"password\":\"" + clientRegisterDTO.getPassword()
                                + "\",\"balance\":" + clientRegisterDTO.getBalance()
                                + ",\"phone\":\"" + clientRegisterDTO.getPhone()
                                + "\",\"govId\":\"" + clientRegisterDTO.getGovId()
                                + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":\"" + clientDTO.getId()
                        + "\",\"name\":\"" + clientDTO.getName()
                        + "\",\"lastName\":\"" + clientDTO.getLastName()
                        + "\",\"username\":\"" + clientDTO.getUsername()
                        + "\",\"email\":\"" + clientDTO.getEmail()
                        + "\",\"balance\":" + clientDTO.getBalance()
                        + "}"));
    } */
}