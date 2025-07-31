package com.proyecto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "Factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Factura")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_Cedula_Cliente", referencedColumnName = "PK_Cedula")
    private Usuario cliente;
    
    @Column(name = "Numero_Factura", unique = true)
    private  String numeroFactura;

    @NotNull(message = "{factura.monto.notnull}")
    @Column(name = "Monto")
    private Double monto;

    @NotNull(message = "{factura.fecha.notnull}")
    @Column(name = "Fecha")
    private LocalDate fecha;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Estado_Factura")
    private boolean estadoFactura;

    public boolean isEstadoFactura() {
        return estadoFactura;
    }
    
}