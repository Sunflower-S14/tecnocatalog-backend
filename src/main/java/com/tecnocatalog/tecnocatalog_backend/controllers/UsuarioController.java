package com.tecnocatalog.tecnocatalog_backend.controllers;

import com.tecnocatalog.tecnocatalog_backend.dto.UsuarioResponse;
import com.tecnocatalog.tecnocatalog_backend.dto.RegistroUsuarioRequest;
import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;
import com.tecnocatalog.tecnocatalog_backend.services.UsuarioService;
import com.tecnocatalog.tecnocatalog_backend.repositories.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(this::convertirAResponse)
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorId(
            @PathVariable Integer id) {

        Usuarios usuario = usuarioService.obtenerUsuarioPorId(id);

        return ResponseEntity.ok(convertirAResponse(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuarios usuario) {

        Usuarios usuarioActualizado =
                usuarioService.actualizarUsuario(id, usuario);

        return ResponseEntity.ok(convertirAResponse(usuarioActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Integer id) {

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    private UsuarioResponse convertirAResponse(Usuarios usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getNumeroCelular(),
                usuario.getEmail(),
                usuario.getRol().getIdrol()
        );
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrarUsuario(
            @Valid @RequestBody RegistroUsuarioRequest request) {

        UsuarioResponse usuario =
                usuarioService.registrarUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    //Endpoints para favoritos
    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<?> obtenerFavoritos(
            @PathVariable Integer usuarioId,
            Authentication authentication) {

        verificarAccesoUsuario(usuarioId, authentication);

        return ResponseEntity.ok(
                usuarioService.obtenerFavoritos(usuarioId)
        );
    }

    @PostMapping("/{usuarioId}/favoritos/{productoId}")
    public ResponseEntity<Void> agregarFavorito(
            @PathVariable Integer usuarioId,
            @PathVariable Integer productoId,
            Authentication authentication) {

        verificarAccesoUsuario(usuarioId, authentication);

        usuarioService.agregarFavorito(usuarioId, productoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}/favoritos/{productoId}")
    public ResponseEntity<Void> eliminarFavorito(
            @PathVariable Integer usuarioId,
            @PathVariable Integer productoId,
            Authentication authentication) {

        verificarAccesoUsuario(usuarioId, authentication);

        usuarioService.eliminarFavorito(usuarioId, productoId);

        return ResponseEntity.noContent().build();
    }

    private void verificarAccesoUsuario(
            Integer usuarioId,
            Authentication authentication) {

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ID_1"));

        if (esAdministrador) {
            return;
        }

        Integer usuarioAutenticado = usuarioRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "No se pudo identificar al usuario autenticado"
                        ))
                .getId();

        if (!usuarioAutenticado.equals(usuarioId)) {
            throw new AccessDeniedException(
                    "No tienes permiso para acceder a los favoritos de otro usuario"
            );
        }
    }


}
