package com.gerna_v1.banksystem.models.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDTO {
    private String hash;
    private String salt;
}