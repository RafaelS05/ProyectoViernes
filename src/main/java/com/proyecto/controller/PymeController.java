package com.proyecto.controller;

import com.proyecto.domain.Pyme;
import com.proyecto.domain.Usuario;
import com.proyecto.domain.UsuarioPyme;
import com.proyecto.service.*;
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
import java.util.Optional;

@Controller
@RequestMapping("/pyme")
public class PymeController {

    @Autowired
    private PymeService pymeService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RazonSocialService razonSocialService;

    @Autowired
    private ActividadEconomicaService actividadEconomicaService;

    @Autowired
    private UsuarioPymeService usuarioPymeService;

    @GetMapping("/mi-pyme")
    public String mostrarFormularioRegistro(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        UsuarioPyme usuarioPyme = usuarioPymeService.getPorUsuario(usuario);

        if (usuarioPyme != null) {
            model.addAttribute("pyme", usuarioPyme.getPyme());
            model.addAttribute("modoEdicion", true);
        } else {
            model.addAttribute("pyme", new Pyme());
            model.addAttribute("modoEdicion", false);
        }

        model.addAttribute("razonesSociales", razonSocialService.getTodas());
        model.addAttribute("actividadesEconomicas", actividadEconomicaService.getTodas());

        return "pyme/mi-pyme";
    }

    @PostMapping("/guardar")
    public String guardarPyme(@Valid @ModelAttribute Pyme pyme,
            BindingResult br,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirect,
            Locale locale,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            redirect.addFlashAttribute("errorSesion",
                    messageSource.getMessage("error.sesion.usuario", null, locale));
            return "redirect:/login";
        }

        if (br.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.pyme", br);
            redirect.addFlashAttribute("pyme", pyme);
            return "redirect:/pyme/mi-pyme";
        }

        boolean esNueva = (pyme.getId() == null);

        // Validar si la cédula ya existe solo si es un nuevo registro
        if (esNueva && pymeService.existePorCedula(pyme.getCedulaJuridicaFisica())) {
            redirect.addFlashAttribute("errorCedula",
                    messageSource.getMessage("pyme.cedula.existe", null, locale));
            redirect.addFlashAttribute("pyme", pyme);
            return "redirect:/pyme/mi-pyme";
        }

        // Obtener PyME existente si es edición
        Pyme existente = esNueva ? new Pyme() : pymeService.getPymePorId(pyme.getId());
        if (existente == null && !esNueva) {
            redirect.addFlashAttribute("errorGeneral", "Error al editar la PyME.");
            return "redirect:/pyme/mi-pyme";
        }

        // Copiar datos del formulario a la entidad existente
        existente.setNombreComercial(pyme.getNombreComercial());
        existente.setDireccionFisica(pyme.getDireccionFisica());
        existente.setCorreoEmpresarial(pyme.getCorreoEmpresarial());
        existente.setTelefonoEmpresarial(pyme.getTelefonoEmpresarial());
        existente.setRazonSocial(pyme.getRazonSocial());
        existente.setActividadEconomica(pyme.getActividadEconomica());

        // Asegurar que la cédula no se pierda (solo lectura, pero aún debe mantenerse)
        existente.setCedulaJuridicaFisica(pyme.getCedulaJuridicaFisica());

        // Imagen nueva
        if (!imagenFile.isEmpty()) {
            String urlImagen = firebaseStorageService.cargaImagen(imagenFile, "pymes", pyme.getCedulaJuridicaFisica());
            existente.setImagenPyme(urlImagen);
        }

        // Guardar la PyME
        pymeService.save(existente);

        // Asociar usuario a PyME si es nueva
        if (esNueva) {
            UsuarioPyme usuarioPymeExistente = usuarioPymeService.getPorUsuario(usuario);
            if (usuarioPymeExistente == null) {
                UsuarioPyme usuarioPyme = new UsuarioPyme();
                usuarioPyme.setUsuario(usuario);
                usuarioPyme.setPyme(existente);
                usuarioPyme.setRol("Propietario");
                usuarioPymeService.save(usuarioPyme);
            }
        }

        redirect.addFlashAttribute("mensajeExito",
                messageSource.getMessage("pyme.guardado.ok", null, locale));

        return "redirect:/pyme/mi-pyme";
    }

}
