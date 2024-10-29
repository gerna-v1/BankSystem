package com.gerna_v1.banksystem.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public abstract class UserEntity {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private PasswordEntity password;
    @Id
    private String id;
    private Long uuid;
}
