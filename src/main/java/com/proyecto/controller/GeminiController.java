package com.proyecto.controller;

import com.proyecto.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/ai")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping
    public String aiPage() {
        return "ia/ia"; 
    }

    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> generate(@RequestBody Map<String, String> body) {
        try {
            String prompt = body.getOrDefault("prompt", "Explain how AI works in a few words");
            String text = geminiService.generate(prompt);
            return ResponseEntity.ok(Map.of("ok", true, "text", text));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", ex.getMessage()));
        }
    }
}
