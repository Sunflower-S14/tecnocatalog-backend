package com.tecnocatalog.tecnocatalog_backend.repositories;

import com.tecnocatalog.tecnocatalog_backend.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Roles, Integer>{
}
