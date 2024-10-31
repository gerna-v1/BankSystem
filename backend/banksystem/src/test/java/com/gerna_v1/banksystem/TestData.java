package com.gerna_v1.banksystem;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.PasswordDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.PasswordEntity;

public final class TestData {
    private TestData() {
        // Private constructor to prevent instantiation
    }

    public static ClientDTO testClient() {
        return ClientDTO.builder()
                .id("1234567")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password(testPasswordDTO())
                .build();
    }

    public static ClientEntity testClientEntity() {
        return ClientEntity.builder()
                .id("1234567")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password(testPasswordEntity())
                .build();
    }

    public static PasswordDTO testPasswordDTO() {
        return PasswordDTO.builder()
                .hash("hashedPassword123")
                .salt("salt123")
                .build();
    }

    public static PasswordEntity testPasswordEntity() {
        return PasswordEntity.builder()
                .hash("hashedPassword123")
                .salt("salt123")
                .build();
    }
}