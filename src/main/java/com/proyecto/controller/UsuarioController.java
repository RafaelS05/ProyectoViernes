package com.proyecto.controller;

import com.proyecto.domain.Usuario;
import com.proyecto.service.FirebaseStorageService;
import com.proyecto.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private MessageSource messageSource;

    // FORMULARIO DE REGISTRO
    @GetMapping("/usuario/registro")
    public String formulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/usuario";
    }

    // GUARDAR USUARIO NUEVO
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
            String urlImagen = firebaseStorageService.cargaImagen(imagenFile, "usuarios", idImg);
            usuario.setImagenPerfil(urlImagen);
        }

        usuarioService.save(usuario);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                messageSource.getMessage("usuario.guardado.ok", null, locale));

        return "redirect:/pyme/registro";
    }

    // LOGIN FORM
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "usuario/login";
    }

    // PROCESAR LOGIN
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirect) {

        Usuario u = usuarioService.getUsuarioPorCorreo(username);

        if (u != null && u.getContrasena().equals(password)) {
            session.setAttribute("usuarioLogueado", u);
            return "redirect:/usuario/perfil";
        }

        redirect.addAttribute("error", true);
        return "redirect:/login";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    // VER PERFIL
    @GetMapping("/usuario/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuarioLogueado);
        return "usuario/perfil";
    }

    // GUARDAR CAMBIOS PERFIL
    @PostMapping("/usuario/perfil/guardar")
    public String guardarPerfil(@Valid @ModelAttribute Usuario usuario,
            BindingResult br,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            HttpSession session,
            RedirectAttributes redirect,
            Locale locale) {

        if (br.hasErrors()) {
            return "usuario/perfil";
        }

        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioActual == null) {
            return "redirect:/login";
        }

        boolean hayCambios = false;

        // Comparar nombre
        if (!usuario.getNombreCompleto().equals(usuarioActual.getNombreCompleto())) {
            usuarioActual.setNombreCompleto(usuario.getNombreCompleto());
            hayCambios = true;
        }

        // Comparar correo
        if (!usuario.getCorreo().equals(usuarioActual.getCorreo())) {
            usuarioActual.setCorreo(usuario.getCorreo());
            hayCambios = true;
        }

        // Comparar y actualizar contraseña si se ingresó una nueva
        if (usuario.getContrasena() != null && !usuario.getContrasena().isBlank()) {
            if (!usuario.getContrasena().equals(usuarioActual.getContrasena())) {
                usuarioActual.setContrasena(usuario.getContrasena());
                hayCambios = true;
            }
        }

        // Manejar imagen
        if (!imagenFile.isEmpty()) {
            long idImg = Math.abs(usuarioActual.getCedula().hashCode());
            String urlImagen = firebaseStorageService.cargaImagen(imagenFile, "usuarios", idImg);
            usuarioActual.setImagenPerfil(urlImagen);
            hayCambios = true;
        }

        if (hayCambios) {
            usuarioService.save(usuarioActual);
            session.setAttribute("usuarioLogueado", usuarioActual);
            redirect.addFlashAttribute("mensajeExito",
                    messageSource.getMessage("usuario.perfil.guardado", null, locale));
        } else {
            redirect.addFlashAttribute("mensajeExito", "No se realizaron cambios.");
        }

        return "redirect:/usuario/perfil";
    }

}
