package io.edpn.backend.exploration.application.domain;

import java.util.Objects;
import java.util.UUID;

public record System(
        UUID id,
        Long eliteId,
        String name,
        String starClass,
        Coordinate coordinate
) {

    public System withEliteId(Long eliteId) {
        return Objects.equals(this.eliteId, eliteId) ? this : new System(id, eliteId, name, starClass, coordinate);
    }

    public System withStarClass(String starClass) {
        return Objects.equals(this.starClass, starClass) ? this : new System(id, eliteId, name, starClass, coordinate);
    }

    public System withCoordinate(Coordinate coordinate) {
        return Objects.equals(this.coordinate, coordinate) ? this : new System(id, eliteId, name, starClass, coordinate);
    }
}
