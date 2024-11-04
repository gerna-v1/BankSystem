package com.gerna_v1.banksystem.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("Session")
public class SessionEntity {

    @Id
    private String uuid;
    private String token;
}