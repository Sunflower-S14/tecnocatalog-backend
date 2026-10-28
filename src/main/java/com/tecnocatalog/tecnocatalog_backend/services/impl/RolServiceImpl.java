package com.tecnocatalog.tecnocatalog_backend.services.impl;

import com.tecnocatalog.tecnocatalog_backend.entities.Roles;
import com.tecnocatalog.tecnocatalog_backend.services.RolService;
import com.tecnocatalog.tecnocatalog_backend.repositories.RolRepository;


import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Roles> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Roles obtenerRolPorId(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("No se encontró el rol con ID: " + id));
    }

    @Override
    public Roles crearRol(Roles rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Roles actualizarRol(Integer id, Roles rol) {
        Roles rolExistente = obtenerRolPorId(id);

        rolExistente.setNombre(rol.getNombre());

        return rolRepository.save(rolExistente);
    }

    @Override
    public void eliminarRol(Integer id) {

        Roles rolExistente = obtenerRolPorId(id);
        rolRepository.delete(rolExistente);
    }

}
