package com.proyecto.controller;

import com.proyecto.domain.Factura;
import com.proyecto.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto.service.FacturaService;
import com.proyecto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/factura/registro")
    public String registroConUsuario(Model model) {
        Usuario us = usuarioService.getUsuarioPorCedula("30209090"); // o una cedula fija de prueba
        if (us == null) {
            us = new Usuario();
        }

        model.addAttribute("usuario", us);
        model.addAttribute("factura", new Factura());
        model.addAttribute("usuarios", usuarioService.getUsuarios());
        model.addAttribute("facturas", facturaService.getFacturas());
        return "factura/factura";
    }

    //Registrar facturas
    @GetMapping("/factura")
    public String registro(Model model) {
        var facturas = facturaService.getFacturas();
        var usuarios = usuarioService.getUsuarios();

        model.addAttribute("factura", new Factura());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("facturas", facturas);
        model.addAttribute("usuario", new Usuario());
        

        return "factura/factura";
    }

    @Autowired
    private MessageSource messageSource;

// Precargar datos del cliente
    @GetMapping("/factura/usuario")
    public String IdCliente(@RequestParam ("cedula") String cedula, Model model) {
        Usuario us = usuarioService.getUsuarioPorCedula(cedula);
        if (us == null) {
            return "redirect:/factura";
        }
        model.addAttribute("usuario", us);
        System.out.println("DEBUG USUARIO   " + us);
        return "factura/factura";
    }

    // Guardar factura
    @PostMapping("/factura/guardar")
    public String guardar(@Valid Factura factura,
            BindingResult br,
            RedirectAttributes redirectAttributes,
            Locale locale,
            Model model) {

        if (br.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.getUsuarios());
            model.addAttribute("facturas", facturaService.getFacturas());
            model.addAttribute("usuario", new Usuario());
            return "factura/factura";
        }

        facturaService.save(factura);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                messageSource.getMessage("factura.guardada", null, locale));

        return "redirect:/factura";
    }
}