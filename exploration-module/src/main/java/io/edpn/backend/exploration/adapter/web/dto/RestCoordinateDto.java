package io.edpn.backend.exploration.adapter.web.dto;

import io.edpn.backend.exploration.application.dto.web.object.CoordinateDto;

public record RestCoordinateDto(Double x,
                                Double y,
                                Double z) implements CoordinateDto {
}
