package com.tecnocatalog.tecnocatalog_backend.services;

import com.tecnocatalog.tecnocatalog_backend.dto.RegistroUsuarioRequest;
import com.tecnocatalog.tecnocatalog_backend.dto.UsuarioResponse;
import com.tecnocatalog.tecnocatalog_backend.entities.Productos;
import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;

import java.util.List;
import java.util.Set;

public interface UsuarioService {

    List<Usuarios> listarUsuarios();

    Usuarios obtenerUsuarioPorId(Integer id);

    Usuarios obtenerUsuarioPorEmail(String email);

    Usuarios actualizarUsuario(Integer id, Usuarios usuario);

    void eliminarUsuario(Integer id);

    UsuarioResponse registrarUsuario(RegistroUsuarioRequest request);

    Set<Productos> obtenerFavoritos(Integer usuarioId);

    void agregarFavorito(Integer usuarioId, Integer productoId);

    void eliminarFavorito(Integer usuarioId, Integer productoId);
}
