package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.SessionEntity;
import com.gerna_v1.banksystem.repositories.SessionRepository;
import com.gerna_v1.banksystem.services.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionManagerImpl implements SessionManager {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionManagerImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void createSession(String uuid, String token) {
        SessionEntity session = new SessionEntity();
        session.setUuid(uuid);
        session.setToken(token);
        System.out.println("Session created: " + session.getUuid() + " " + session.getToken());
        sessionRepository.save(session);
    }

    @Override
    public void deleteSession(String uuid) {
        System.out.println("Session deleted: " + uuid);
        sessionRepository.deleteById(uuid);
    }

    @Override
    public boolean isSessionActive(String uuid) {
        return sessionRepository.existsById(uuid);
    }

    @Override
    public void deleteAllSessions() {
        sessionRepository.deleteAll();
    }
}