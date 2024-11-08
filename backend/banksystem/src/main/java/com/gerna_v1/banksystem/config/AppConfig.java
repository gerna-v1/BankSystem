package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.UserEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.implementations.BCryptPasswordManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<ClientEntity> clientEntity = clientRepository.findByUsername(username);

            if (clientEntity.isPresent()) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(clientEntity.get().getUsername())
                        .password(clientEntity.get().getPassword().getHash())
                        .authorities("CLIENT")
                        .build();
            }

            Optional<AdminEntity> adminEntity = adminRepository.findByUsername(username);

            if (adminEntity.isPresent()) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(adminEntity.get().getUsername())
                        .password(adminEntity.get().getPassword().getHash())
                        .authorities("ADMIN")
                        .build();
            }

            throw new UsernameNotFoundException("User not found");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordManager();
    }
}