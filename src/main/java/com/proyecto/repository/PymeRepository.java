package com.proyecto.repository;

import com.proyecto.domain.Pyme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PymeRepository extends JpaRepository<Pyme, Long> {
    
}

