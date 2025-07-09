package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.domain.Usuario;
import com.proyecto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.proyecto.service.FirebaseStorageService;
import java.util.Locale;
import org.springframework.context.MessageSource;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/registro")
    public String formulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/usuario";
    }
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/usuario/guardar")
    public String guardar(@Valid Usuario usuario,
            BindingResult br,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        if (usuarioService.existeCedula(usuario.getCedula())) {
            br.rejectValue("cedula", "usuario.cedula.duplicada");
        }
        Usuario existeCorreo = usuarioService.getUsuarioPorCorreo(usuario.getCorreo());
        if (existeCorreo != null && !existeCorreo.getCedula().equals(usuario.getCedula())) {
            br.rejectValue("correo", "usuario.correo.duplicado");
        }
        if (br.hasErrors()) {
            return "usuario/usuario";
        }

        if (!imagenFile.isEmpty()) {
            long idImg = Math.abs(usuario.getCedula().hashCode());  
            String urlImagen = firebaseStorageService.cargaImagen(
                    imagenFile, "usuarios", idImg);
            usuario.setImagenPerfil(urlImagen);
        }

        usuarioService.save(usuario);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                messageSource.getMessage("usuario.guardado.ok", null, locale));

        return "redirect:/pyme/registro";
    }

}
