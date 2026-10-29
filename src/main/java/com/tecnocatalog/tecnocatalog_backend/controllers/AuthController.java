package com.tecnocatalog.tecnocatalog_backend.controllers;

import com.tecnocatalog.tecnocatalog_backend.dto.AuthResponse;
import com.tecnocatalog.tecnocatalog_backend.dto.LoginRequest;
import com.tecnocatalog.tecnocatalog_backend.services.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse respuesta = authService.iniciarSesion(request);

        return ResponseEntity.ok(respuesta);
    }
}
