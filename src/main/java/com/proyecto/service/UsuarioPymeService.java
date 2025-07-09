package com.proyecto.service;

import com.proyecto.domain.UsuarioPyme;
import com.proyecto.repository.UsuarioPymeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioPymeService {

    @Autowired
    private UsuarioPymeRepository usuarioPymeRepository;

    @Transactional(readOnly = true)
    public UsuarioPyme getUsuarioPyme(UsuarioPyme usuarioPyme) {
        return usuarioPymeRepository.findById(usuarioPyme.getId()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<UsuarioPyme> getUsuarioPymes() {
        return usuarioPymeRepository.findAll();
    }

    @Transactional
    public void save(UsuarioPyme usuarioPyme) {
        usuarioPymeRepository.save(usuarioPyme);
    }

    @Transactional
    public boolean delete(UsuarioPyme usuarioPyme) {
        try {
            usuarioPymeRepository.delete(usuarioPyme);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

