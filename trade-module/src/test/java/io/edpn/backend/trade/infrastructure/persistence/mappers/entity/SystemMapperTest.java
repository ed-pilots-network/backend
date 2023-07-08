package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.infrastructure.persistence.entity.SystemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SystemMapperTest {

    private SystemMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemMapper();
    }

    @Test
    public void shouldMapSystemEntityToSystem() {
        UUID id = UUID.randomUUID();
        Long eliteId = 123456L;
        String name = "System Name";
        Double xCoordinate = 123.45;
        Double yCoordinate = 678.90;
        Double zCoordinate = 234.56;
        SystemEntity systemEntity = SystemEntity.builder()
                .id(id)
                .eliteId(eliteId)
                .name(name)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();

        System system = underTest.map(systemEntity);

        assertThat(system.getId(), is(id));
        assertThat(system.getEliteId(), is(eliteId));
        assertThat(system.getName(), is(name));
        assertThat(system.getXCoordinate(), is(xCoordinate));
        assertThat(system.getYCoordinate(), is(yCoordinate));
        assertThat(system.getZCoordinate(), is(zCoordinate));
    }

    @Test
    public void shouldMapSystemToSystemEntity() {
        UUID id = UUID.randomUUID();
        Long eliteId = 123456L;
        String name = "System Name";
        Double xCoordinate = 123.45;
        Double yCoordinate = 678.90;
        Double zCoordinate = 234.56;
        System system = System.builder()
                .id(id)
                .eliteId(eliteId)
                .name(name)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();

        SystemEntity systemEntity = underTest.map(system);

        assertThat(systemEntity.getId(), is(id));
        assertThat(systemEntity.getEliteId(), is(eliteId));
        assertThat(systemEntity.getName(), is(name));
        assertThat(systemEntity.getXCoordinate(), is(xCoordinate));
        assertThat(systemEntity.getYCoordinate(), is(yCoordinate));
        assertThat(systemEntity.getZCoordinate(), is(zCoordinate));
    }
}
