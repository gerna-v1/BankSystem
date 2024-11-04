package com.gerna_v1.banksystem.models.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
