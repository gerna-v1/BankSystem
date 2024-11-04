package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.services.implementations.AdminUserDetailsService;
import com.gerna_v1.banksystem.services.implementations.ClientUserDetailsService;
import com.gerna_v1.banksystem.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AdminUserDetailsService adminUserDetailsService;
    private final ClientUserDetailsService clientUserDetailsService;
    private final PasswordManager passwordManager;

    @Autowired
    public CustomAuthenticationProvider(AdminUserDetailsService adminUserDetailsService,
                                        ClientUserDetailsService clientUserDetailsService,
                                        PasswordManager passwordManager) {
        this.adminUserDetailsService = adminUserDetailsService;
        this.clientUserDetailsService = clientUserDetailsService;
        this.passwordManager = passwordManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails;
        try {
            userDetails = adminUserDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            userDetails = clientUserDetailsService.loadUserByUsername(username);
        }

        if (passwordManager.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            throw new AuthenticationException("Invalid credentials") {};
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}