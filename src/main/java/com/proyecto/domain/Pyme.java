package com.proyecto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pyme")
@Data
public class Pyme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_Pyme")
    private Long id;

    @Column(name = "Cedula_juridica_fisica", nullable = false, unique = true)
    @NotBlank(message = "{pyme.cedulaJuridicaFisica.requerido}")
    private String cedulaJuridicaFisica;

    @ManyToOne
    @JoinColumn(name = "FK_Razon_Social", nullable = false)
    private RazonSocial razonSocial;

    @Column(name = "Nombre_comercial")
    private String nombreComercial;

    @ManyToOne
    @JoinColumn(name = "FK_Actividad_Economica", nullable = false)
    private ActividadEconomica actividadEconomica;

    @Column(name = "Direccion_fisica")
    private String direccionFisica;

    @Column(name = "Correo_empresarial")
    private String correoEmpresarial;

    @Column(name = "Telefono_empresarial")
    private String telefonoEmpresarial;

    @Column(name = "Imagen_Pyme", length = 1024)
    private String imagenPyme;

    @Column(name = "Fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public void setImagenPyme(String imagenPyme) {
        this.imagenPyme = imagenPyme;
    }

    public String getImagenPyme() {
        return this.imagenPyme;
    }

}
