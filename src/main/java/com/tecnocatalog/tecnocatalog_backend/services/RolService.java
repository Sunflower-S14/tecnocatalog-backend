package com.tecnocatalog.tecnocatalog_backend.services;

import com.tecnocatalog.tecnocatalog_backend.entities.Roles;

import java.util.List;

public interface RolService {
    List<Roles> listarRoles();

    Roles obtenerRolPorId(Integer id);

    Roles crearRol(Roles rol);

    Roles actualizarRol(Integer id, Roles rol);

    void eliminarRol(Integer id);
}
