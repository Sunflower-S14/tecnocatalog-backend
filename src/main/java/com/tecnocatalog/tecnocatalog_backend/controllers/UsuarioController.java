package com.tecnocatalog.tecnocatalog_backend.controllers;

import com.tecnocatalog.tecnocatalog_backend.dto.UsuarioResponse;
import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;
import com.tecnocatalog.tecnocatalog_backend.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
}
