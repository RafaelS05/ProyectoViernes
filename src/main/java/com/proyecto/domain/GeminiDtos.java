package com.proyecto.domain;

import java.util.List;

public class GeminiDtos {
    public record Part(String text) {}
    public record Content(List<Part> parts) {}
    public record GenerateRequest(List<Content> contents) {}

    public record Candidate(Content content) {}
    public record GenerateResponse(List<Candidate> candidates) {}
}

