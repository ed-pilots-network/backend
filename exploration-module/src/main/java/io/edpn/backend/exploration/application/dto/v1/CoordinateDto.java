package io.edpn.backend.exploration.application.dto.v1;

public record CoordinateDto(Double x, Double y, Double z) implements io.edpn.backend.exploration.domain.dto.v1.CoordinateDto {
}
