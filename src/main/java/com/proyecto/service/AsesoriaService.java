package com.proyecto.service;

import com.proyecto.domain.Asesoria;
import com.proyecto.repository.AsesoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsesoriaService {

    @Autowired
    private AsesoriaRepository asesoriaRepository;

    @Transactional(readOnly = true)
    public List<Asesoria> getAsesorias() {
        return asesoriaRepository.findAll();
    }

    @Transactional
    public void save(Asesoria asesoria) {
        asesoriaRepository.save(asesoria);
    }

    @Transactional
    public boolean delete(Asesoria asesoria) {
        try {
            asesoriaRepository.delete(asesoria);
            asesoriaRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
