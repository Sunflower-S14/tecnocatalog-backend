package com.tecnocatalog.tecnocatalog_backend.controllers;

import com.tecnocatalog.tecnocatalog_backend.entities.Roles;
import com.tecnocatalog.tecnocatalog_backend.services.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<Roles>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> obtenerRolPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                rolService.obtenerRolPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Roles> crearRol(
            @RequestBody Roles rol) {

        Roles nuevoRol = rolService.crearRol(rol);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoRol);
    }

    // Actualizar un rol
    @PutMapping("/{id}")
    public ResponseEntity<Roles> actualizarRol(
            @PathVariable Integer id,
            @RequestBody Roles rol) {

        return ResponseEntity.ok(
                rolService.actualizarRol(id, rol)
        );
    }

    // Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(
            @PathVariable Integer id) {

        rolService.eliminarRol(id);

        return ResponseEntity.noContent().build();
    }
}
