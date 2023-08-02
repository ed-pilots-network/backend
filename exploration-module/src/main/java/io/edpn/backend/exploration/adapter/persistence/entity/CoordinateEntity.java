package io.edpn.backend.exploration.adapter.persistence.entity;

public record CoordinateEntity(Double x,
                               Double y,
                               Double z) implements io.edpn.backend.exploration.application.dto.CoordinateDto {
}
