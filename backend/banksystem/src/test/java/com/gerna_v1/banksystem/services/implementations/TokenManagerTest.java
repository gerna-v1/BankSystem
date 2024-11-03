package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.services.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenManagerTest {

    private final TokenManager tokenManagerService;
    private String token;

    @Autowired
    public TokenManagerTest(TokenManagerImpl tokenManagerService) {
        this.tokenManagerService = tokenManagerService;
    }

    @BeforeEach
    void setUp() {
        String username = TestData.testClient().getUsername();
        String email = TestData.testClient().getEmail();
        String role = "ROLE_USER";
        String id = TestData.testClient().getId();
        int accessLevel = 1;
        token = tokenManagerService.generateToken(username, email, role, id, accessLevel);
    }

    @Test
    void testGenerateToken() {
        String username = TestData.testClient().getUsername();
        String email = TestData.testClient().getEmail();
        String role = "ROLE_USER";
        String id = TestData.testClient().getId();
        int accessLevel = 1;
        String generatedToken = tokenManagerService.generateToken(username, email, role, id, accessLevel);

        assertNotNull(generatedToken);
        assertTrue(tokenManagerService.validateToken(generatedToken));
    }

    @Test
    void testValidateToken() {
        boolean isValid = tokenManagerService.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testGetUsernameFromToken() {
        String username = TestData.testClient().getUsername();
        String extractedUsername = tokenManagerService.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testGetEmailFromToken() {
        String email = TestData.testClient().getEmail();
        String extractedEmail = tokenManagerService.getEmailFromToken(token);

        assertEquals(email, extractedEmail);
    }

    @Test
    void testGetRoleFromToken() {
        String role = "ROLE_USER";
        String extractedRole = tokenManagerService.getRoleFromToken(token);

        assertEquals(role, extractedRole);
    }
}