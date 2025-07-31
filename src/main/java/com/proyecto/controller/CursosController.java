package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CursosController {

    @GetMapping("/cursos")
    public String mostrarCurso() {
        return "cursos/cursos"; 
    }
    
    @GetMapping("/cursoexcel")
    public String mostrarCursoExcel() {
        return "cursos/cursoexcel"; 
    }
}