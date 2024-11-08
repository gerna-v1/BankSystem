package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordManagerTest {

    private final BCryptPasswordManager underTest;

    @Autowired
    public PasswordManagerTest(@Qualifier("BCryptPasswordManager") BCryptPasswordManager underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testHashPassword() {
        String password = "mySecurePassword";
        String salt = underTest.generateSalt();
        String hashedPassword = underTest.hashPassword(password, salt);
        PasswordEntity passwordEntity = underTest.passwordToEntity(hashedPassword, salt);

        assertNotNull(passwordEntity);
        assertNotEquals(password, passwordEntity.getHash());
    }

    @Test
    public void testCheckPassword() {
        String password = "mySecurePassword";
        String salt = underTest.generateSalt();
        String hashedPassword = underTest.hashPassword(password, salt);
        PasswordEntity passwordEntity = underTest.passwordToEntity(hashedPassword, salt);

        assertTrue(underTest.matches(password, passwordEntity.getHash(), passwordEntity.getSalt()));
        assertFalse(underTest.matches("wrongPassword", passwordEntity.getHash(), passwordEntity.getSalt()));
    }

    @Test
    public void testGenerateSalt() {
        String salt1 = underTest.generateSalt();
        String salt2 = underTest.generateSalt();

        assertNotNull(salt1);
        assertNotNull(salt2);
        assertNotEquals(salt1, salt2);
    }
}