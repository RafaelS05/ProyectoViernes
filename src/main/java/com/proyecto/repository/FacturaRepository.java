package com.proyecto.repository;

import com.proyecto.domain.Factura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
    List<Factura> findByCliente_Cedula (String cedula);
    
}


