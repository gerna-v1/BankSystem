package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import com.gerna_v1.banksystem.services.PasswordManager;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class BCryptPasswordManager implements PasswordManager {

    @Override
    public String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public PasswordEntity passwordToEntity(String hashedPassword, String salt) {
        return PasswordEntity.builder()
                .hash(hashedPassword)
                .salt(salt)
                .build();
    }

    @Override
    public boolean checkPassword(String password, String hashedPassword, String salt) {
        String hashedAttempt = BCrypt.hashpw(password, salt);
        return hashedAttempt.equals(hashedPassword);
    }

    @Override
    public String generateSalt() {
        return BCrypt.gensalt();
    }

    @Override
    public boolean matches(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}