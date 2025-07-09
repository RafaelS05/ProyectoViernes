package com.proyecto.service;

import com.proyecto.domain.Pyme;
import com.proyecto.repository.PymeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PymeService {

    @Autowired
    private PymeRepository pymeRepository;

    @Transactional(readOnly = true)
    public Pyme getPyme(Pyme pyme) {
        return pymeRepository.findById(pyme.getId()).orElse(null);

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
}
