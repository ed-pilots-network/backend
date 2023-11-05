package io.edpn.backend.exploration.adapter.web.dto;

import io.edpn.backend.exploration.application.dto.web.object.CoordinateDto;
import io.edpn.backend.exploration.application.dto.web.object.SystemDto;

public record RestSystemDto(String name,
                            CoordinateDto coordinate,
                            Long eliteId,
                            String primaryStarClass) implements SystemDto {
}
