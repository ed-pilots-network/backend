package io.edpn.backend.exploration.adapter.web.dto;

public record RestCoordinateDto(Double x,
                                Double y,
                                Double z) implements io.edpn.backend.exploration.application.dto.CoordinateDto {
}
