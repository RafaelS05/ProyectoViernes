package com.proyecto.repository;

import com.proyecto.domain.Pyme;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PymeRepository extends JpaRepository<Pyme, Long> {

    boolean existsByCedulaJuridicaFisica(String cedulaJuridicaFisica);

    Optional<Pyme> findByCedulaJuridicaFisica(String cedulaJuridicaFisica);

    @Query("SELECT p FROM Pyme p JOIN UsuarioPyme up ON up.pyme = p WHERE up.usuario.cedula = :cedulaUsuario")
    Pyme findByUsuarioCedula(@Param("cedulaUsuario") String cedulaUsuario);
    
}
