package com.proyecto.service;

import com.proyecto.domain.Usuario;
import com.proyecto.domain.UsuarioPyme;
import com.proyecto.repository.UsuarioPymeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioPymeService {

    @Autowired
    private UsuarioPymeRepository usuarioPymeRepository;

    @Transactional(readOnly = true)
    public UsuarioPyme getPorUsuario(Usuario usuario) {
        return usuarioPymeRepository.findByUsuario(usuario);
    }

    @Transactional
    public void save(UsuarioPyme usuarioPyme) {
        usuarioPymeRepository.save(usuarioPyme);
    }

    @Transactional
    public void delete(UsuarioPyme usuarioPyme) {
        usuarioPymeRepository.delete(usuarioPyme);
    }
}


