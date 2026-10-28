package com.tecnocatalog.tecnocatalog_backend.repositories;

import com.tecnocatalog.tecnocatalog_backend.entities.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categorias, Integer>{
}
