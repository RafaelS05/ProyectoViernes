package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Razon_Social")
@Data
public class RazonSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Razon")
    private Integer id;

    @Column(name = "Nombre_Razon", nullable = false, unique = true)
    private String nombre;
}


