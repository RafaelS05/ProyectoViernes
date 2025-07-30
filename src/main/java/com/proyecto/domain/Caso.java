/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Casos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Caso")
    private int idCaso;

    @Column(name = "FK_Cedula_Usuario")
    private String cedulaUsuario;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Rating")
    private int rating;

    @Column(name = "Fecha_Creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_Cedula_Usuario", referencedColumnName = "PK_Cedula", insertable = false, updatable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

}
