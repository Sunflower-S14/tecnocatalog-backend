package com.tecnocatalog.tecnocatalog_backend.repositories;

import com.tecnocatalog.tecnocatalog_backend.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {

    Optional<Usuarios> findByEmail(String email);

    boolean existsByEmail(String email);
}
