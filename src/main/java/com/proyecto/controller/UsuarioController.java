package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.domain.Usuario;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class UsuarioController {

    @Autowired private UsuarioService usuarioService;

    @GetMapping("/usuario/registro")
    public String formulario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuario/usuario";
    }

    @PostMapping("/usuario/registrar")
    public String registrar(@Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult br){
        if(br.hasErrors()){
            return "usuario/usuario";
        }
        usuarioService.save(usuario);
        return "redirect:/usuario/registro?exito";
    }
}





