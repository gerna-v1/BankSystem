package com.gerna_v1.banksystem.services;

public interface SessionManager {
    void createSession(String uid, String token);
    void deleteSession(String uid);
    boolean isSessionActive(String uid);
    void deleteAllSessions();
}
