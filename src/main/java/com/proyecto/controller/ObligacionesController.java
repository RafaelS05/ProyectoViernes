package com.proyecto.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class ObligacionesController {

    @GetMapping("/obligaciones")
    public String verObligaciones() {
        return "obligaciones/obligaciones";
    }

   @GetMapping("/formalizacion")
public String verGuiaFormalizacion(HttpSession session) {
    if (session.getAttribute("usuarioLogueado") == null) {
        session.setAttribute("redirigirDespues", "/formalizacion"); 
        return "redirect:/login";
    }
    return "formalizacion/formalizacion";
}
}