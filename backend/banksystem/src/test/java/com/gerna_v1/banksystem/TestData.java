package com.gerna_v1.banksystem;

import com.gerna_v1.banksystem.models.DTOs.*;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.PasswordEntity;

public final class TestData {
    private TestData() {
        // Private constructor to prevent instantiation
    }

    public static ClientDTO testClientDTO() {
        return ClientDTO.builder()
                .id("1234567")
                .name("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john.doe@example.com")
                .balance(1000.0)
                .build();
    }

    public static ClientRegisterDTO testClientRegister() {
        return ClientRegisterDTO.builder()
                .name("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john.doe@example.com")
                .password("password123")
                .balance(1000.0)
                .phone("123-456-7890")
                .govId("A1B2C3D4")
                .build();
    }

    public static ClientEntity testClientEntity() {
        return ClientEntity.builder()
                .id("1234567")
                .name("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john.doe@example.com")
                .password(testPasswordEntity())
                .balance(1000.0)
                .phone("123-456-7890")
                .govId("A1B2C3D4")
                .build();
    }

    public static AdminRegisterDTO testAdminRegister() {
        return AdminRegisterDTO.builder()
                .name("Jane")
                .lastName("Doe")
                .username("janedoe")
                .email("jane.doe@example.com")
                .password("password123")
                .accessLevel(1)
                .build();
    }

    public static AdminEntity testAdminEntity() {
        return AdminEntity.builder()
                .id("7654321")
                .name("Jane")
                .lastName("Doe")
                .username("janedoe")
                .email("jane.doe@example.com")
                .password(testPasswordEntity())
                .accessLevel(1)
                .build();
    }

    public static AdminDTO testAdminDTO() {
        return AdminDTO.builder()
                .id("7654321")
                .username("janedoe")
                .email("jane.doe@example.com")
                .accessLevel(1)
                .build();
    }

    public static PasswordEntity testPasswordEntity() {
        return PasswordEntity.builder()
                .hash("hashedPassword123")
                .salt("salt123")
                .build();
    }

    public static LoginRequest testLoginRequest() {
        return new LoginRequest("pepe", "password123");
    }
}