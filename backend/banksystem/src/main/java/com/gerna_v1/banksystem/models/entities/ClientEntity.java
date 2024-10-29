package com.gerna_v1.banksystem.models.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "clients")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity extends UserEntity{
    private double balance;
    private String phone;
    private String govId;

    @Builder
    public ClientEntity(String name, String lastName, String username, String email, PasswordEntity password, String id, Long uuid, double balance, String phone, String govId) {
        super(name, lastName, username, email, password, id, uuid);
        this.balance = balance;
        this.phone = phone;
        this.govId = govId;
    }
}