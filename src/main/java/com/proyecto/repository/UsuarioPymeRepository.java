package com.proyecto.repository;

import com.proyecto.domain.Usuario;
import com.proyecto.domain.UsuarioPyme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioPymeRepository extends JpaRepository<UsuarioPyme, Long> {
    
    UsuarioPyme findByUsuario(Usuario usuario);
}


