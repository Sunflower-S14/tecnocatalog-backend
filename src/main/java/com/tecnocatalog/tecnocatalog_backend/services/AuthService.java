package com.tecnocatalog.tecnocatalog_backend.services;

import com.tecnocatalog.tecnocatalog_backend.dto.AuthResponse;
import com.tecnocatalog.tecnocatalog_backend.dto.LoginRequest;

public interface AuthService {

    AuthResponse iniciarSesion(LoginRequest request);

}
