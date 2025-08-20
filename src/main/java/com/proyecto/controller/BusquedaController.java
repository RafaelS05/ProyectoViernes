package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.Normalizer;

@Controller
public class BusquedaController {

    @GetMapping("/buscar")
    public String buscar(@RequestParam(value = "q", required = false) String q) {
        String original = (q == null) ? "" : q.trim();
        if (original.isEmpty()) {
            return "redirect:/";
        }
        String term = normalizar(original);

        if (term.contains("asesoria")){
            return "redirect:/asesoria/asesoria";
            
        } else if (term.contains("ayuda"))  {
            return "redirect:/ayuda";
            
        } else if (term.contains("caso") || term.contains("casos")) {
            return "redirect:/casos/lista";
            
        } else if (term.contains("curso") || term.contains("cursos")) {
            return "redirect:/cursos";
            
        } else if (term.contains("factura") || term.contains("facturacion")) {
            return "redirect:/factura/";
            
        } else if (term.contains("obligacion") || term.contains("obligaciones")) {
            return "redirect:/obligaciones";
            
        } else if (term.contains("pregunta") || term.contains("faq")) {
            return "redirect:/preguntas";
            
        } else if (term.contains("recomendacion") || term.contains("recomendaciones")) {
            return "redirect:/recomendaciones";
            
        } else if (term.contains("soporte") || term.contains("ticket")) {
            return "redirect:/soporte";
        }

        String url = UriComponentsBuilder.fromPath("/resultados")
                .queryParam("q", original)
                .build().toString();
        return "redirect:" + url;
    }

    private String normalizar(String s) {
        String n = Normalizer.normalize(s, Normalizer.Form.NFD);
        n = n.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return n.toLowerCase();
    }
}
