/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.repository;

import com.proyecto.domain.Caso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasosRepository extends JpaRepository<Caso, Integer> {
    List<Caso> findByCedulaUsuario(String cedulaUsuario);
  

}

