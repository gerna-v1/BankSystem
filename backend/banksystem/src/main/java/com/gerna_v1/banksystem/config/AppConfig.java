package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.services.PasswordManager;
import com.gerna_v1.banksystem.services.implementations.BCryptPasswordManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public PasswordManager passwordManager() {
        return new BCryptPasswordManager();
    }
}
