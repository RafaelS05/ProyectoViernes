/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.controller;

import com.proyecto.domain.Pyme;
import com.proyecto.domain.Usuario;
import com.proyecto.domain.Recomendacion;
import com.proyecto.service.PymeService;
import com.proyecto.service.RecomendacionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recomendaciones")
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @Autowired
    private PymeService pymeService;

    @GetMapping
    public String mostrarRecomendaciones(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        Pyme pyme = pymeService.obtenerPorUsuario(usuario.getCedula());

        model.addAttribute("tienePyme", pyme != null && pyme.getActividadEconomica() != null);

        if (pyme == null || pyme.getActividadEconomica() == null) {
            model.addAttribute("mensajeError", "No tiene una PyME asociada o no tiene actividad económica.");
            return "recomendaciones/recomendaciones";
        }

        List<Recomendacion> lista = recomendacionService.obtenerPorActividad(pyme.getActividadEconomica());
        model.addAttribute("recomendaciones", lista);
        model.addAttribute("actividadNombre", pyme.getActividadEconomica().getNombre());

        System.out.println("Usuario logueado: " + usuario.getCedula());
        System.out.println("PyME encontrada: " + (pyme != null ? pyme.getNombreComercial() : "null"));

        return "recomendaciones/recomendaciones";
    }

}
