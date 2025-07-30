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
    public List<Factura> getFacturas(){
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
}