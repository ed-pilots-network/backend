package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Ship;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShipEntityMapperTest {
    private Ship ship;
    private ShipEntity shipEntity;

    @BeforeEach
    public void setUp() {
        ship = Ship.builder()
                .id(1L)
                .name("Test Ship")
                .build();

        shipEntity = ShipEntity.builder()
                .id(1L)
                .name("Test Ship")
                .build();
    }

    @Test
    public void map_shipToShipEntity() {
        Optional<ShipEntity> mappedEntity = ShipEntity.Mapper.map(ship);
        assertThat(mappedEntity.isPresent(), is(true));
        assertThat(mappedEntity.get(), is(shipEntity));
    }

    @Test
    public void map_shipEntityToShip() {
        Optional<Ship> mappedShip = ShipEntity.Mapper.map(shipEntity);
        assertThat(mappedShip.isPresent(), is(true));
        assertThat(mappedShip.get(), is(ship));
    }
}
