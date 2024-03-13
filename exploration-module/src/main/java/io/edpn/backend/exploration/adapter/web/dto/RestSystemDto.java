package io.edpn.backend.exploration.adapter.web.dto;

public record RestSystemDto(String name,
                            RestCoordinateDto coordinate,
                            Long eliteId,
                            String primaryStarClass) {
}
