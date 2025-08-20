package com.proyecto.service;

import com.proyecto.domain.GeminiDtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    @Value("${google.gemini.apiKey}")
    private String apiKey;

    @Value("${google.gemini.model}")
    private String model;

    @Value("${google.gemini.baseUrl}")
    private String baseUrl;

    private final RestTemplate rest = new RestTemplate();

    public String generate(String prompt) {
        String url = String.format("%s/v1beta/models/%s:generateContent?key=%s",
                baseUrl, model, apiKey);

        var request = new GenerateRequest(
                List.of(new Content(List.of(new Part(prompt))))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GenerateRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GenerateResponse> resp =
                rest.exchange(url, HttpMethod.POST, entity, GenerateResponse.class);

        GenerateResponse body = resp.getBody();
        if (body == null || body.candidates() == null || body.candidates().isEmpty()) {
            return "(sin contenido)";
        }
        var first = body.candidates().get(0);
        if (first.content() == null || first.content().parts() == null || first.content().parts().isEmpty()) {
            return "(sin contenido)";
        }
        return first.content().parts().get(0).text();
    }
}

