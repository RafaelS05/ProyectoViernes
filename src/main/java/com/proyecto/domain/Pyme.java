package com.proyecto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pyme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pyme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_Pyme")
    private Long id;

    @Column(name = "Razon_social")
    @NotBlank(message = "{pyme.razon.notblank}")
    private String razonSocial;

    @Column(name = "Nombre_comercial")
    private String nombreComercial;

    @Column(name = "Actividad_economica")
    private String actividadEconomica;

    @Column(name = "Direccion_fisica")
    private String direccionFisica;

    @Column(name = "Correo_empresarial")
    private String correoEmpresarial;

    @Column(name = "Telefono_empresarial")
    private String telefonoEmpresarial;

    @Column(name = "Imagen_Pyme") 
    private String imagenPyme; 

    @Column(name = "Fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}

