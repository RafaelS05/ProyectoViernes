/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.repository;

import com.proyecto.domain.Recomendacion;
import com.proyecto.domain.ActividadEconomica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    List<Recomendacion> findByActividadEconomica(ActividadEconomica actividad);
}

