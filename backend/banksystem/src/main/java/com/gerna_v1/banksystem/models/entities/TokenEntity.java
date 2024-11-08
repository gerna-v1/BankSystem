package com.gerna_v1.banksystem.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("TokenEntity")
public class TokenEntity {

    public enum TokenType {
        BEARER
    }

    @Id
    private String id;

    private TokenType tokenType = TokenType.BEARER;

    @Indexed
    private String username;

    @Indexed
    private String token;

    private boolean revoked;

    private boolean expired;
}