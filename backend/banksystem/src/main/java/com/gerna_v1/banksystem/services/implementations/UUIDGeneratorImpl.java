package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.services.UUIDGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGeneratorImpl implements UUIDGenerator {
    @Override
    public String generateUuid(String email) {
        return UUID.nameUUIDFromBytes(email.getBytes()).toString();
    }
}
