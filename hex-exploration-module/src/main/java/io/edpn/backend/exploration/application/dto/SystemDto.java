package io.edpn.backend.exploration.application.dto;

public interface SystemDto {
    String name();

    CoordinateDto coordinates();

    Long eliteId();

    String starClass();
}
