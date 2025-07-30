package com.proyecto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "PK_Cedula", length = 20)
    private String cedula;

    @Column(name = "Nombre_Completo")
    @Size(max = 100)
    private String nombreCompleto;

    @Column(name = "Correo", unique = true)
    @Email(message = "{usuario.correo.email}")
    private String correo;

    @Column(name = "Contraseña")
    private String contrasena;

    @Column(name = "Imagen_Perfil", length = 1024)
    private String imagenPerfil;

    @Column(name = "Fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
