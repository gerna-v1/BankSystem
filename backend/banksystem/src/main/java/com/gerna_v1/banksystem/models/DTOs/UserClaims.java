package com.gerna_v1.banksystem.models.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserClaims {
    private String username;
    private String email;
    private String id;
    private int accessLevel;
}