package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Productos;
import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;
import com.tecnocatalog.tecnocatalog_backend.repositories.ProductoRepository;
import com.tecnocatalog.tecnocatalog_backend.repositories.UsuarioRepository;
import com.tecnocatalog.tecnocatalog_backend.services.UsuarioService;
import org.springframework.stereotype.Service;

import com.tecnocatalog.tecnocatalog_backend.dto.RegistroUsuarioRequest;
import com.tecnocatalog.tecnocatalog_backend.dto.UsuarioResponse;
import com.tecnocatalog.tecnocatalog_backend.entities.Roles;
import com.tecnocatalog.tecnocatalog_backend.repositories.RolRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository , ProductoRepository productoRepository ,
                              RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
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
    @Transactional
    public UsuarioResponse registrarUsuario(RegistroUsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException(
                    "Ya existe un usuario registrado con ese correo electrónico"
            );
        }

        // El registro público siempre se le asignará el rol de usuario (ID 2).
        Roles rol = rolRepository.findById(2)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No se encontró el rol de usuario con ID 2"
                        ));

        Usuarios usuario = new Usuarios();

        usuario.setNombre(request.nombre());
        usuario.setNumeroCelular(request.numeroCelular());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        usuario.setRol(rol);

        Usuarios usuarioGuardado = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                usuarioGuardado.getId(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getNumeroCelular(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getRol().getIdrol()
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

    // Lógica de negocio para "favoritos"

    @Override
    @Transactional(readOnly = true)
    public Set<Productos> obtenerFavoritos(Integer usuarioId) {
        Usuarios usuario = obtenerUsuarioPorId(usuarioId);
        return usuario.getFavoritos();
    }

    @Override
    @Transactional
    public void agregarFavorito(Integer usuarioId, Integer productoId) {
        Usuarios usuario = obtenerUsuarioPorId(usuarioId);

        Productos producto = productoRepository.findById(productoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró el producto con ID: " + productoId
                        ));

        usuario.getFavoritos().add(producto);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarFavorito(Integer usuarioId, Integer productoId) {
        Usuarios usuario = obtenerUsuarioPorId(usuarioId);

        boolean eliminado = usuario.getFavoritos()
                .removeIf(producto ->
                        producto.getIdproducto().equals(productoId));

        if (!eliminado) {
            throw new RuntimeException(
                    "El producto con ID " + productoId
                            + " no está en los favoritos del usuario " + usuarioId
            );
        }

        usuarioRepository.save(usuario);
    }
}
