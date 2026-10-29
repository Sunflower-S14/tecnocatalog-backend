package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.dto.AuthResponse;
import com.tecnocatalog.tecnocatalog_backend.dto.LoginRequest;
import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;
import com.tecnocatalog.tecnocatalog_backend.repositories.UsuarioRepository;
import com.tecnocatalog.tecnocatalog_backend.security.JwtService;
import com.tecnocatalog.tecnocatalog_backend.services.AuthService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse iniciarSesion(LoginRequest request) {

        Usuarios usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new BadCredentialsException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(
                request.password(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        Integer rolId = usuario.getRol().getIdrol();

        String token = jwtService.generarToken(
                usuario.getId(),
                usuario.getEmail(),
                rolId
        );

        return new AuthResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                rolId
        );
    }
}
