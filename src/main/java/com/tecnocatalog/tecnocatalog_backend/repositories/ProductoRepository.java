package com.tecnocatalog.tecnocatalog_backend.repositories;

import com.tecnocatalog.tecnocatalog_backend.entities.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Productos, Integer> {
}
