package com.gerna_v1.banksystem.services;

public interface SessionManager {
    void createSession(String token);
    void deleteSession(String token);
    boolean isSessionActive(String token);
    void deleteAllSessions();
    void deleteAllSessionsByUserId(String userId);
}