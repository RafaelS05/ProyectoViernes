package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario_Pyme")
@Data
public class UsuarioPyme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario_Pyme")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FK_Cedula", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_ID_Pyme", nullable = false)
    private Pyme pyme;

    @Column(name = "Rol", nullable = false)
    private String rol = "Propietario";

    @Column(name = "Fecha_asociacion", updatable = false)
    private LocalDateTime fechaAsociacion;

    @PrePersist
    public void prePersist() {
        this.fechaAsociacion = LocalDateTime.now();
    }
}

