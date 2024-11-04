package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.services.implementations.AdminUserDetailsService;
import com.gerna_v1.banksystem.services.implementations.ClientUserDetailsService;
import com.gerna_v1.banksystem.services.PasswordManager;
import com.gerna_v1.banksystem.services.UUIDGenerator;
import com.gerna_v1.banksystem.services.implementations.BCryptPasswordManager;
import com.gerna_v1.banksystem.services.implementations.UUIDGeneratorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final AdminUserDetailsService adminUserDetailsService;
    private final ClientUserDetailsService clientUserDetailsService;
    private final PasswordManager passwordManager;

    @Bean
    public PasswordManager passwordManager() {
        return new BCryptPasswordManager();
    }

    @Bean
    public UUIDGenerator uuidGenerator() {
        return new UUIDGeneratorImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(adminUserDetailsService, clientUserDetailsService, passwordManager);
    }
}