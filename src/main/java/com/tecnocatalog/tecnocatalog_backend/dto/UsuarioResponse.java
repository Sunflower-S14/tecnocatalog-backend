package com.tecnocatalog.tecnocatalog_backend.dto;

public record UsuarioResponse(
        Integer id,
        String nombre,
        String numeroCelular,
        String email,
        Integer rolId
) {
}
