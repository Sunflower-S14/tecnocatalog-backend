package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Categorias;
import com.tecnocatalog.tecnocatalog_backend.services.CategoriaService;

import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {

    @Override
    public List<Categorias> listarCategorias() {
        return List.of();
    }

    @Override
    public Categorias obtenerCategoriaPorId(Integer id) {
        return null;
    }

    @Override
    public Categorias crearCategoria(Categorias categoria) {
        return null;
    }

    @Override
    public Categorias actualizarCategoria(Integer id, Categorias categoria) {
        return null;
    }

    @Override
    public void eliminarCategoria(Integer id) {

    }
}
