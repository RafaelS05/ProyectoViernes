/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Recomendacion")
@Data
public class Recomendacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecomendacion;

    @ManyToOne
    @JoinColumn(name = "FK_Actividad_Economica", nullable = false)
    private ActividadEconomica actividadEconomica;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String fuente;

    private LocalDate fechaPublicacion = LocalDate.now();
}

