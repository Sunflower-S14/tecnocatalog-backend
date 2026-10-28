package com.tecnocatalog.tecnocatalog_backend.controllers;

import com.tecnocatalog.tecnocatalog_backend.entities.Categorias;
import com.tecnocatalog.tecnocatalog_backend.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categorias>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorias> obtenerCategoriaPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                categoriaService.obtenerCategoriaPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Categorias> crearCategoria(
            @RequestBody Categorias categoria) {

        Categorias nuevaCategoria =
                categoriaService.crearCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorias> actualizarCategoria(
            @PathVariable Integer id,
            @RequestBody Categorias categoria) {

        return ResponseEntity.ok(
                categoriaService.actualizarCategoria(id, categoria)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Integer id) {

        categoriaService.eliminarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}
