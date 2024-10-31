package com.gerna_v1.banksystem.models.DTOs;

import lombok.Data;

@Data
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