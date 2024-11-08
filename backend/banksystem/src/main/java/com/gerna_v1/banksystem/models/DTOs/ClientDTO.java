package com.gerna_v1.banksystem.models.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String govId;
    private double balance;
}

