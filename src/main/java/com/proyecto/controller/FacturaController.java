package com.proyecto.controller;

import com.proyecto.domain.Factura;
import com.proyecto.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto.service.FacturaService;
import com.proyecto.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/factura/registro")
    public String registroConUsuario(Model model, HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // Obtener usuario persistente
        Usuario usuarioPersistente = usuarioService.getUsuarioPorCedula(usuarioLogueado.getCedula());

        Factura nuevaFactura = new Factura();
        nuevaFactura.setCliente(usuarioPersistente);

        // Generar número de factura incremental y formateado
        List<Factura> facturas = facturaService.getFacturas();
        int nuevoNumero = facturas.stream()
                .mapToInt(f -> {
                    try {
                        return Integer.parseInt(f.getNumeroFactura().replace("F-", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0) + 1;

        String numeroFormateado = String.format("F-%04d", nuevoNumero);
        nuevaFactura.setNumeroFactura(numeroFormateado);

        model.addAttribute("factura", nuevaFactura);
        model.addAttribute("usuario", usuarioPersistente);
        model.addAttribute("usuarios", usuarioService.getUsuarios());
        model.addAttribute("facturas", facturas);

        return "factura/factura";
    }

    @Autowired
    private MessageSource messageSource;

    // Guardar factura
    @PostMapping("/factura/guardar")
    public String guardar(@Valid Factura factura,
            BindingResult br,
            RedirectAttributes redirectAttributes,
            Locale locale,
            Model model,
            HttpSession session) {

        if (br.hasErrors()) {
            model.addAttribute("factura", factura);
            model.addAttribute("usuarios", usuarioService.getUsuarios());
            model.addAttribute("facturas", facturaService.getFacturas());
            model.addAttribute("usuario", new Usuario());
            return "factura/factura";
        }

        // Volver a obtener el cliente persistente desde la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado != null) {
            Usuario usuarioPersistente = usuarioService.getUsuarioPorCedula(usuarioLogueado.getCedula());
            factura.setCliente(usuarioPersistente);
        }

        // Asegurar que se asigna el número si no viene ya
        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isEmpty()) {
            String numeroFormateado = facturaService.generarSiguienteNumeroFactura();
            factura.setNumeroFactura(numeroFormateado);
        }

        facturaService.save(factura);

        redirectAttributes.addFlashAttribute(messageSource.getMessage("Factura.guardada", null, locale));

        return "redirect:/factura";
    }
}
