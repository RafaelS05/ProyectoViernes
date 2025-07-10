package com.proyecto.controller;

import com.proyecto.domain.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.service.FacturaService;
import com.proyecto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;
import org.springframework.context.MessageSource;

@Controller
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private UsuarioService usuarioService;
    
    
    //Registrar facturas
    @GetMapping("/factura/registro")
    public String registro(Model model) {
        var facturas = facturaService.getFacturas();
        var usuarios = usuarioService.getUsuarios();
        
        model.addAttribute("factura", new Factura());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("facturas", facturas);
        
        return "factura/factura";
    }

    @Autowired
    private MessageSource messageSource;

    
    //Guardar facturas
    @PostMapping("/factura/guardar")
    public String guardar(@Valid Factura factura,
            BindingResult br,
            RedirectAttributes redirectAttributes,
            Locale locale,
            Model model) {

        if (br.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.getUsuarios());
            model.addAttribute("facturas", facturaService.getFacturas());
            return "factura/factura";
        }

        facturaService.save(factura);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                messageSource.getMessage("factura.guardada", null, Locale.getDefault()));

        return "redirect:/factura/registro";
    }
}
