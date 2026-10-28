package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Productos;
import com.tecnocatalog.tecnocatalog_backend.services.ProductoService;

import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    @Override
    public List<Productos> listarProductos() {
        return List.of();
    }

    @Override
    public Productos obtenerProductoPorId(Integer id) {
        return null;
    }

    @Override
    public Productos crearProducto(Productos producto) {
        return null;
    }

    @Override
    public Productos actualizarProducto(Integer id, Productos producto) {
        return null;
    }

    @Override
    public void eliminarProducto(Integer id) {

    }
}
