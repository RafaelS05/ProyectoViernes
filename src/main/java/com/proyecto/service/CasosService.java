/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;

import com.proyecto.domain.Caso;
import com.proyecto.repository.CasosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PC
 */
@Service
public class CasosService {

    @Autowired
    private CasosRepository casosRepository;

    @Transactional
    public void save(Caso caso) {
        casosRepository.save(caso);
    }

    @Transactional
    public boolean delete(Caso caso) {
        try {
            casosRepository.delete(caso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Caso getCasoPorId(int id) {
        return casosRepository.findById(id).orElse(null);
    }

}
