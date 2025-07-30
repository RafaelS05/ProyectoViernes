/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;

import com.proyecto.domain.Recomendacion;
import com.proyecto.domain.ActividadEconomica;
import com.proyecto.repository.RecomendacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecomendacionService {

    @Autowired
    private RecomendacionRepository recomendacionRepository;

    @Transactional(readOnly = true)
    public List<Recomendacion> obtenerPorActividad(ActividadEconomica actividad) {
        return recomendacionRepository.findByActividadEconomica(actividad);
    }

    @Transactional
    public void guardar(Recomendacion recomendacion) {
        recomendacionRepository.save(recomendacion);
    }

    @Transactional
    public boolean eliminar(Long id) {
        try {
            recomendacionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Recomendacion> obtenerTodas() {
        return recomendacionRepository.findAll();
    }
}

