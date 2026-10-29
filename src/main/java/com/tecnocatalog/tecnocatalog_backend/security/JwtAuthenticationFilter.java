package com.tecnocatalog.tecnocatalog_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {
            Claims claims = jwtService.validarToken(token);

            String email = claims.getSubject();
            Number rolId = claims.get("rolId", Number.class);

            if (email == null || email.isBlank() || rolId == null) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Token inválido"
                );
                return;
            }

            if (SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                var autoridad = new SimpleGrantedAuthority(
                        "ROLE_ID_" + rolId.intValue()
                );

                var autenticacion =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(autoridad)
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(autenticacion);
            }

        } catch (JwtException | IllegalArgumentException ex) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token inválido o expirado"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
