package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordManagerTest {

    private BCryptPasswordManager passwordManager;

    @Autowired
    public void setPasswordManager(BCryptPasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Test
    public void testHashPassword() {
        String password = "mySecurePassword";
        String salt = passwordManager.generateSalt();
        String hashedPassword = passwordManager.hashPassword(password, salt);
        PasswordEntity passwordEntity = passwordManager.passwordToEntity(hashedPassword, salt);

        assertNotNull(passwordEntity);
        assertNotEquals(password, passwordEntity.getHash());
    }

    @Test
    public void testCheckPassword() {
        String password = "mySecurePassword";
        String salt = passwordManager.generateSalt();
        String hashedPassword = passwordManager.hashPassword(password, salt);
        PasswordEntity passwordEntity = passwordManager.passwordToEntity(hashedPassword, salt);

        assertTrue(passwordManager.checkPassword(password, passwordEntity.getHash(), passwordEntity.getSalt()));
        assertFalse(passwordManager.checkPassword("wrongPassword", passwordEntity.getHash(), passwordEntity.getSalt()));
    }

    @Test
    public void testGenerateSalt() {
        String salt1 = passwordManager.generateSalt();
        String salt2 = passwordManager.generateSalt();

        assertNotNull(salt1);
        assertNotNull(salt2);
        assertNotEquals(salt1, salt2);
    }
}