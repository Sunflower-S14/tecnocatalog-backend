package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Roles;
import com.tecnocatalog.tecnocatalog_backend.services.RolService;
import com.tecnocatalog.tecnocatalog_backend.repositories.RolRepository;


import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Override
    public List<Roles> listarRoles() {
        return null;
    }

    @Override
    public Roles obtenerRolPorId(Integer id) {
        return null;
    }

    @Override
    public Roles crearRol(Roles rol) {
        return null;
    }

    @Override
    public Roles actualizarRol(Integer id, Roles rol) {
        return null;
    }

    @Override
    public void eliminarRol(Integer id) {

    }

}
