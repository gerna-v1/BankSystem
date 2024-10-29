package com.gerna_v1.banksystem.models.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PasswordEntity {
    private String hash;
    private String salt;
}