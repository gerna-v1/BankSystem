package com.gerna_v1.banksystem.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegisterDTO {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Integer accessLevel;
}