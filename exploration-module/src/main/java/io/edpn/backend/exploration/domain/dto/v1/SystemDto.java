package io.edpn.backend.exploration.domain.dto.v1;

public interface SystemDto {
    String name();

    CoordinateDto coordinates();

    Long eliteId();

    String starClass();
}
