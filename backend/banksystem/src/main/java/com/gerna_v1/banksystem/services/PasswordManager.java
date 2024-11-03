package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import org.springframework.stereotype.Component;

@Component
public interface PasswordManager {
    String hashPassword(String password, String salt);
    PasswordEntity passwordToEntity(String hashedPassword, String salt);
    boolean checkPassword(String password, String hashedPassword, String salt);
    String generateSalt();
}
