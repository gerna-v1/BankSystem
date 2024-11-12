package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.services.SessionManager;
import com.gerna_v1.banksystem.services.implementations.JwtService;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailService;
    private final SessionManager sessionManager;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth") && !request.getServletPath().contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or does not start with Bearer");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        final String username;
        try {
            username = jwtService.extractUsername(jwtToken);
        } catch (SignatureException e) {
            logger.warn("JWT signature does not match locally computed signature", e);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (username == null) {
            logger.warn("User email is null");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (!sessionManager.isSessionActive(jwtToken)) {
            logger.warn("Token not found in repository");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
        final Optional<ClientEntity> client = clientRepository.findByUsername(userDetails.getUsername());
        final Optional<AdminEntity> admin = adminRepository.findByUsername(userDetails.getUsername());

        if (client.isEmpty() && admin.isEmpty()) {
            logger.warn("User not found in repository");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final boolean isTokenValid;
        if (client.isPresent()) {
            isTokenValid = jwtService.isTokenValid(jwtToken, client.get());
        } else if (admin.isPresent()) {
            isTokenValid = jwtService.isTokenValid(jwtToken, admin.get());
        } else {
            isTokenValid = false;
        }

        if (!isTokenValid) {
            logger.warn("Token is not valid");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String role = jwtService.extractRole(jwtToken);
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authorities
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}