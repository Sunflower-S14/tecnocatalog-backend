package com.tecnocatalog.tecnocatalog_backend.services;

import com.tecnocatalog.tecnocatalog_backend.entities.Categorias;

import java.util.List;

public interface CategoriaService {
    List<Categorias> listarCategorias();

    Categorias obtenerCategoriaPorId(Integer id);

    Categorias crearCategoria(Categorias categoria);

    Categorias actualizarCategoria(Integer id, Categorias categoria);

    void eliminarCategoria(Integer id);
}
