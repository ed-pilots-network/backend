package io.edpn.backend.exploration.application.dto.persistence.entity;

import java.util.UUID;

public interface SystemEntity {

    UUID getId();

    String getName();

    Long getEliteId();

    String getStarClass();

    Double getXCoordinate();

    Double getYCoordinate();

    Double getZCoordinate();
}
