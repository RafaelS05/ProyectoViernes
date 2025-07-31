package com.proyecto.service;

import com.proyecto.domain.Factura;
import com.proyecto.repository.FacturaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional(readOnly = true)
    public Factura getFactura(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Factura> getFacturas() {
        return facturaRepository.findAll();
    }

    @Transactional
    public void save(Factura factura) {
        facturaRepository.save(factura);
    }

    @Transactional
    public boolean delete(Factura factura) {
        try {
            facturaRepository.delete(factura);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public String generarSiguienteNumeroFactura() {
        String ultimoNumero = facturaRepository.obtenerUltimoNumeroFactura();
        int numero = 0;

        if (ultimoNumero != null && ultimoNumero.startsWith("F-")) {
            try {
                numero = Integer.parseInt(ultimoNumero.replace("F-", ""));
            } catch (NumberFormatException e) {
                numero = 0;
            }
        }

        numero++;
        return String.format("F-%04d", numero);
    }
}
