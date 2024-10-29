package com.gerna_v1.banksystem.models.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminEntity extends UserEntity{
    private Integer accessLevel;

    @Builder
    public AdminEntity(String name, String lastName, String username, String email, PasswordEntity password, String id, Long uuid, Integer accessLevel) {
        super(name, lastName, username, email, password, id, uuid);
        this.accessLevel = accessLevel;
    }
}
