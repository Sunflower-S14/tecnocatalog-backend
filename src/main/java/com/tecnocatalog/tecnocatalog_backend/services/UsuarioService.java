package com.tecnocatalog.tecnocatalog_backend.services;

import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;

import java.util.List;

public interface UsuarioService {

    List<Usuarios> listarUsuarios();

    Usuarios obtenerUsuarioPorId(Integer id);

    Usuarios obtenerUsuarioPorEmail(String email);

    Usuarios actualizarUsuario(Integer id, Usuarios usuario);

    void eliminarUsuario(Integer id);
}
