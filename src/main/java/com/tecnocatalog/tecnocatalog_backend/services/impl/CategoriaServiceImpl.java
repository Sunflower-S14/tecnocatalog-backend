package com.tecnocatalog.tecnocatalog_backend.services.impl;
import org.springframework.stereotype.Service;
import com.tecnocatalog.tecnocatalog_backend.entities.Categorias;
import com.tecnocatalog.tecnocatalog_backend.repositories.CategoriaRepository;
import com.tecnocatalog.tecnocatalog_backend.services.CategoriaService;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Categorias> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categorias obtenerCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No se encontró la categoría con ID: " + id
                        ));
    }

    @Override
    public Categorias crearCategoria(Categorias categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categorias actualizarCategoria(Integer id, Categorias categoria) {
        Categorias categoriaExistente = obtenerCategoriaPorId(id);

        categoriaExistente.setNombre(categoria.getNombre());
        categoriaExistente.setDescripcion(categoria.getDescripcion());

        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    public void eliminarCategoria(Integer id) {
        Categorias categoriaExistente = obtenerCategoriaPorId(id);
        categoriaRepository.delete(categoriaExistente);
    }
}
