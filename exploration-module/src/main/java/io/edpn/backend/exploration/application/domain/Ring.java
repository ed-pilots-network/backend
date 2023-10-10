package io.edpn.backend.exploration.application.domain;

public record Ring(
        Long innerRadius,
        Long mass, // MT MegaTonnes
        String name,
        Long outerRadius,
        String ringClass) {
}