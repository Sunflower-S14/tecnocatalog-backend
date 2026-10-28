package com.tecnocatalog.tecnocatalog_backend.services;

import com.tecnocatalog.tecnocatalog_backend.entities.Productos;

import java.util.List;

public interface ProductoService {

    List<Productos> listarProductos();

    Productos obtenerProductoPorId(Integer id);

    Productos crearProducto(Productos producto);

    Productos actualizarProducto(Integer id, Productos producto);

    void eliminarProducto(Integer id);
}
