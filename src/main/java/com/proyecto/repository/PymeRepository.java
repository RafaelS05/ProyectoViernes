package com.proyecto.repository;

import com.proyecto.domain.Pyme;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PymeRepository extends JpaRepository<Pyme, Long> {

    boolean existsByCedulaJuridicaFisica(String cedulaJuridicaFisica);
    Optional<Pyme> findByCedulaJuridicaFisica(String cedulaJuridicaFisica);

}
