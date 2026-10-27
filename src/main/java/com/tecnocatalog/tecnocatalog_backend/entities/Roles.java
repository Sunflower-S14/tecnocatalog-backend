package com.tecnocatalog.tecnocatalog_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private Integer idrol;

    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;
}
