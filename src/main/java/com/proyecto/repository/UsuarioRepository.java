package com.proyecto.repository;

import com.proyecto.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Override
    boolean existsById(String cedula);
    Usuario findByCorreo(String correo);
    Usuario findByCedula(String cedula);
    
    
    
}


