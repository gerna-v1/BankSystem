package com.gerna_v1.banksystem.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClientRegisterDTO {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private double balance;
    private String phone;
    private String govId;
}