/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;

import com.proyecto.domain.RazonSocial;
import com.proyecto.repository.RazonSocialRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazonSocialService {

    @Autowired
    private RazonSocialRepository razonSocialRepository;

    public List<RazonSocial> getTodas() {
        return razonSocialRepository.findAll();
    }
}
