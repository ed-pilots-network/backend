package io.edpn.backend.exploration.application.dto.v1;

public record SystemDto(String name, io.edpn.backend.exploration.domain.dto.v1.CoordinateDto coordinates, Long eliteId, String starClass) implements io.edpn.backend.exploration.domain.dto.v1.SystemDto {
}
