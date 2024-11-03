package com.gerna_v1.banksystem.models.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
