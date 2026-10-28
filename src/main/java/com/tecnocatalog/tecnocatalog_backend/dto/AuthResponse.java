package com.tecnocatalog.tecnocatalog_backend.dto;

public record AuthResponse(
        String token,
        String tipo,
        Integer usuarioId,
        String nombre,
        String email,
        Integer rolId
) {

}
