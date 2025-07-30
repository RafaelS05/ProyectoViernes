package com.proyecto.repository;

import com.proyecto.domain.Usuario;
import com.proyecto.domain.UsuarioPyme;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioPymeRepository extends JpaRepository<UsuarioPyme, Long> {

    @Query("SELECT up FROM UsuarioPyme up WHERE up.usuario.cedula = :cedula")
    Optional<UsuarioPyme> findByCedulaUsuario(@Param("cedula") String cedula);

    UsuarioPyme findByUsuario(Usuario usuario);

}
