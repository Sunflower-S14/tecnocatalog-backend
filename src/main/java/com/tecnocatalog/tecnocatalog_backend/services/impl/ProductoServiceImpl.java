package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Categorias;
import com.tecnocatalog.tecnocatalog_backend.entities.Productos;
import com.tecnocatalog.tecnocatalog_backend.repositories.CategoriaRepository;
import com.tecnocatalog.tecnocatalog_backend.repositories.ProductoRepository;
import com.tecnocatalog.tecnocatalog_backend.services.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Productos> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Productos obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró el producto con ID: " + id
                        ));
    }

    @Override
    public Productos crearProducto(Productos producto) {
        Integer idCategoria = producto.getCategoria().getIdcategoria();

        Categorias categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró la categoría con ID: " + idCategoria
                        ));

        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    @Override
    public Productos actualizarProducto(Integer id, Productos producto) {
        Productos productoExistente = obtenerProductoPorId(id);

        Integer idCategoria = producto.getCategoria().getIdcategoria();

        Categorias categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró la categoría con ID: " + idCategoria
                        ));

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setStock(producto.getStock());
        productoExistente.setCategoria(categoria);

        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminarProducto(Integer id) {
        Productos productoExistente = obtenerProductoPorId(id);
        productoRepository.delete(productoExistente);
    }
}
