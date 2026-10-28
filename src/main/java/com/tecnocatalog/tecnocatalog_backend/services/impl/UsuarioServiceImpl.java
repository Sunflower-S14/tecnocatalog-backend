package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;
import com.tecnocatalog.tecnocatalog_backend.repositories.UsuarioRepository;
import com.tecnocatalog.tecnocatalog_backend.services.UsuarioService;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuarios obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró el usuario con ID: " + id
                        ));
    }

    @Override
    public Usuarios obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró el usuario con email: " + email
                        )
                );
    }

    @Override
    public Usuarios actualizarUsuario(Integer id, Usuarios usuario) {
        Usuarios usuarioExistente = obtenerUsuarioPorId(id);

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setNumeroCelular(usuario.getNumeroCelular());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setRol(usuario.getRol());

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void eliminarUsuario(Integer id) {
        Usuarios usuarioExistente = obtenerUsuarioPorId(id);
        usuarioRepository.delete(usuarioExistente);
    }
}
