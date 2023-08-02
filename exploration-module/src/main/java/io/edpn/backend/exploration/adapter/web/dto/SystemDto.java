package io.edpn.backend.exploration.adapter.web.dto;

public record SystemDto(String name, io.edpn.backend.exploration.application.dto.CoordinateDto coordinate, Long eliteId, String starClass) implements io.edpn.backend.exploration.application.dto.SystemDto {
}
