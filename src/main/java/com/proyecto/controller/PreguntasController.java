package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PreguntasController {

    @GetMapping("/preguntas")
    public String mostrarPreguntas() {
        return "preguntas/preguntas";
    }
}
