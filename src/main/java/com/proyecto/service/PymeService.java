package com.proyecto.service;

import com.proyecto.domain.Pyme;
import com.proyecto.repository.PymeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PymeService {

    @Autowired
    private PymeRepository pymeRepository;

    @Transactional(readOnly = true)
    public Pyme getPymePorId(Long id) {
        return pymeRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Pyme> getPymes() {
        return pymeRepository.findAll();
    }

    @Transactional
    public void save(Pyme pyme) {
        pymeRepository.save(pyme);
    }

    @Transactional
    public boolean delete(Pyme pyme) {
        try {
            pymeRepository.delete(pyme);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existePorCedula(String cedula) {
        return pymeRepository.existsByCedulaJuridicaFisica(cedula);
    }
    
    public Optional<Pyme> buscarPorCedula(String cedula) {
        return pymeRepository.findByCedulaJuridicaFisica(cedula); // <- NUEVO
    }

}
