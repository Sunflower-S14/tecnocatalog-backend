package com.tecnocatalog.tecnocatalog_backend.controllers;

import com.tecnocatalog.tecnocatalog_backend.entities.Productos;
import com.tecnocatalog.tecnocatalog_backend.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Productos>> listarProductos() {
        return ResponseEntity.ok(
                productoService.listarProductos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productos> obtenerProductoPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                productoService.obtenerProductoPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Productos> crearProducto(
            @RequestBody Productos producto) {

        Productos nuevoProducto =
                productoService.crearProducto(producto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody Productos producto) {

        return ResponseEntity.ok(
                productoService.actualizarProducto(id, producto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable Integer id) {

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}
