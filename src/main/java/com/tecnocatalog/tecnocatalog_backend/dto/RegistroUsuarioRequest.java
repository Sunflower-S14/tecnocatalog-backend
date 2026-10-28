package com.tecnocatalog.tecnocatalog_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 45, message = "El nombre no puede superar 45 caracteres")
        String nombre,

        @NotBlank(message = "El número celular es obligatorio")
        @Size(min = 10, max = 10,
                message = "El número celular debe tener 10 caracteres")
        String numeroCelular,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no es válido")
        @Size(max = 45, message = "El correo no puede superar 45 caracteres")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 72,
                message = "La contraseña debe tener entre 8 y 72 caracteres")
        String password,

        @NotNull(message = "El ID del rol es obligatorio")
        Integer rolId

) {}
