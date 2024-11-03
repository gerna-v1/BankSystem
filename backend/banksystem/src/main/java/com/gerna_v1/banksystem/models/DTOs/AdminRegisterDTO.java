package com.gerna_v1.banksystem.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRegisterDTO {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Integer accessLevel;
}