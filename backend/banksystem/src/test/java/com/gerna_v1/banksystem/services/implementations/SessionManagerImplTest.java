package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.SessionEntity;
import com.gerna_v1.banksystem.repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private SessionRepository sessionRepository;

    @Autowired
    private SessionManagerImpl sessionManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession() {
        SessionEntity session = new SessionEntity();
        session.setUuid("test-uuid");
        session.setToken("test-token");

        sessionManager.createSession("test-uuid", "test-token");

        verify(sessionRepository, times(1)).save(any(SessionEntity.class));
    }

    @Test
    void testDeleteSession() {
        sessionManager.deleteSession("test-uuid");

        verify(sessionRepository, times(1)).deleteById("test-uuid");
    }

    @Test
    void testIsSessionActive() {
        when(sessionRepository.existsById("test-uuid")).thenReturn(true);

        boolean isActive = sessionManager.isSessionActive("test-uuid");

        assertTrue(isActive);
        verify(sessionRepository, times(1)).existsById("test-uuid");
    }

    @Test
    void testDeleteAllSessions() {
        sessionManager.deleteAllSessions();

        verify(sessionRepository, times(1)).deleteAll();
    }
}