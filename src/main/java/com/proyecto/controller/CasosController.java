/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.controller;

import com.proyecto.domain.Caso;
import com.proyecto.domain.Usuario;
import com.proyecto.service.CasosService;
import com.proyecto.repository.CasosRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/casos")
public class CasosController {

    @Autowired
    private CasosService casoService;

    @Autowired
    private CasosRepository casoRepository;

    @GetMapping("/nuevo")
    public String mostrarFormularioCaso(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        Caso nuevo = new Caso();
        nuevo.setCedulaUsuario(usuario.getCedula());

        model.addAttribute("caso", nuevo);
        return "casos/formulario";
    }

   @PostMapping("/guardar")
public String guardarCaso(@Valid @ModelAttribute("caso") Caso caso,
                          BindingResult result,
                          Model model,
                          HttpSession session) {
    if (result.hasErrors()) {
        return "casos/formulario";
    }

    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        return "redirect:/login";
    }

    caso.setCedulaUsuario(usuario.getCedula());

    if (caso.getIdCaso() > 0) {
        Caso original = casoService.getCasoPorId(caso.getIdCaso());
        if (original != null) {
            caso.setFechaCreacion(original.getFechaCreacion());
        }
    }

    casoService.save(caso);
    return "redirect:/casos/lista/" + usuario.getCedula();
}


    @GetMapping("/lista/{cedula}")
    public String listarCasosPorUsuario(@PathVariable("cedula") String cedula, Model model, HttpSession session) {
        List<Caso> casos = casoRepository.findByCedulaUsuario(cedula);
        model.addAttribute("casos", casos);
        model.addAttribute("cedulaUsuario", cedula);

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        model.addAttribute("usuarioLogueado", usuario); 

        return "casos/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCaso(@PathVariable("id") int id) {
        Caso caso = casoRepository.findById(id).orElse(null);
        if (caso != null) {
            casoService.delete(caso);
        }
        return "redirect:/casos/lista/" + (caso != null ? caso.getCedulaUsuario() : "");
    }

    @GetMapping("/lista")
    public String listarTodos(Model model, HttpSession session) {
        model.addAttribute("casos", casoRepository.findAll());

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        model.addAttribute("usuarioLogueado", usuario);

        return "casos/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarCaso(@PathVariable("id") int id, Model model, HttpSession session) {
        Caso caso = casoRepository.findById(id).orElse(null);
        if (caso == null) {
            return "redirect:/casos/lista";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !usuario.getCedula().equals(caso.getCedulaUsuario())) {
            return "redirect:/casos/lista";
        }

        caso.setCedulaUsuario(usuario.getCedula());

        model.addAttribute("caso", caso);
        return "casos/formulario";
    }

}
