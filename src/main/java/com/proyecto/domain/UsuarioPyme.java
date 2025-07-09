package com.proyecto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario_Pyme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPyme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario_Pyme")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_Cedula", referencedColumnName = "PK_Cedula")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_ID_Pyme", referencedColumnName = "PK_ID_Pyme")
    private Pyme pyme;

    @Column(name = "Rol")
    @NotBlank(message = "{usuariopyme.rol.notblank}")
    private String rol;

    @Column(name = "Fecha_asociacion", updatable = false)
    private LocalDateTime fechaAsociacion;

    @PrePersist
    public void prePersist() {
        this.fechaAsociacion = LocalDateTime.now();
    }
}
