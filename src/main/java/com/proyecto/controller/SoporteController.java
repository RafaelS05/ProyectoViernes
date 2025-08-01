package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SoporteController {

    @GetMapping("/soporte")
    public String mostrarSoporte() {
        return "soporte/soporte";
    }
}
