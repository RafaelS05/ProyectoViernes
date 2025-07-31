package com.proyecto.repository;

import com.proyecto.domain.Factura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByCliente_Cedula(String cedula);

    @Query("SELECT MAX(f.numeroFactura) FROM Factura f")
    String obtenerUltimoNumeroFactura();

}
