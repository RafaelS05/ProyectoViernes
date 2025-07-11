package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Asesoria")
@Data
@NoArgsConstructor
public class Asesoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Asesoria")
    private Long idAsesoria;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Fecha")
    private LocalDateTime fecha;

    @Column(name = "TipoConsulta")
    private String tipoConsulta;
}
