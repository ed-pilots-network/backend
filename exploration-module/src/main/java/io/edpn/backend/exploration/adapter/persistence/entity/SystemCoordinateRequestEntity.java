package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestDto;

public record SystemCoordinateRequestEntity(String systemName,
                                            String requestingModule) implements SystemCoordinateRequestDto {
}
