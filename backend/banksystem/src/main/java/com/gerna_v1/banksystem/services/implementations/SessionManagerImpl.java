package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.entities.TokenEntity;
import com.gerna_v1.banksystem.repositories.TokenRepository;
import com.gerna_v1.banksystem.services.SessionManager;
import com.gerna_v1.banksystem.services.implementations.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionManagerImpl implements SessionManager {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(SessionManagerImpl.class);

    @Autowired
    public SessionManagerImpl(TokenRepository tokenRepository, JwtService jwtService) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void createSession(String token) {
        String uuid = jwtService.extractUuid(token);
        TokenEntity session = new TokenEntity();
        session.setId(uuid);
        session.setToken(token);
        logger.info("Creating session: {} {}", session.getId(), session.getToken());
        tokenRepository.save(session);
        logger.info("Session created: {} {}", session.getId(), session.getToken());
    }

    @Override
    public void deleteSession(String token) {
        String uuid = jwtService.extractUuid(token);
        logger.info("Deleting session: {}", uuid);
        tokenRepository.deleteById(uuid);
        logger.info("Session deleted: {}", uuid);
    }

    @Override
    public boolean isSessionActive(String token) {
        String uuid = jwtService.extractUuid(token);
        boolean isActive = tokenRepository.existsById(uuid);
        logger.info("Checking if session is active for {}: {}", uuid, isActive);
        return isActive;
    }

    @Override
    public void deleteAllSessionsByUserId(String userId) {
        logger.info("Deleting all sessions for user: {}", userId);
        List<TokenEntity> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseById(userId);

        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(t -> {
                t.setRevoked(true);
                t.setExpired(true);
            });

            tokenRepository.saveAll(validUserTokens);
            logger.info("All sessions for user {} have been revoked and expired", userId);
        } else {
            logger.info("No valid sessions found for user: {}", userId);
        }
    }

    @Override
    public void deleteAllSessions() {
        logger.info("Deleting all sessions");
        tokenRepository.deleteAll();
        logger.info("All sessions deleted");
    }
}