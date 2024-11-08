package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.TokenEntity;
import com.gerna_v1.banksystem.repositories.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionManagerImplTest {

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private SessionManagerImpl sessionManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*    @Test
    void testCreateSession() {
        TokenEntity session = new TokenEntity();
        session.setUuid("test-uuid");
        session.setToken("test-token");

        sessionManager.createSession("test-uuid", "test-token");

        verify(tokenRepository, times(1)).save(any(TokenEntity.class));
    }

    @Test
    void testDeleteSession() {
        sessionManager.deleteSession("test-uuid");

        verify(tokenRepository, times(1)).deleteById("test-uuid");
    }

    @Test
    void testIsSessionActive() {
        when(tokenRepository.existsById("test-uuid")).thenReturn(true);

        boolean isActive = sessionManager.isSessionActive("test-uuid");

        assertTrue(isActive);
        verify(tokenRepository, times(1)).existsById("test-uuid");
    }

    @Test
    void testDeleteAllSessions() {
        sessionManager.deleteAllSessions();

        verify(tokenRepository, times(1)).deleteAll();
    } */
}