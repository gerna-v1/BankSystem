package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class BCryptPasswordManager implements PasswordEncoder {

    public String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return hashPassword(rawPassword.toString(), generateSalt());
    }

    public boolean matches(String password, String hashedPassword, String salt) {
        String hashedAttempt = BCrypt.hashpw(password, salt);
        return hashedAttempt.equals(hashedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }

    public PasswordEntity passwordToEntity(String hashedPassword, String salt) {
        return PasswordEntity.builder()
                .hash(hashedPassword)
                .salt(salt)
                .build();
    }

    public String generateSalt() {
        return BCrypt.gensalt();
    }
}