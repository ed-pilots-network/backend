package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SystemMapperTest {

    private SystemMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemMapper();
    }

    @Test
    void shouldMapEntityToModel() {
        UUID id = UUID.fromString("8a6ce07c-ef95-4b65-81b4-94bac1b827bb");
        Long eliteId = 1001L;
        String name = "Test System";
        Double xCoordinate = 100.0;
        Double yCoordinate = 200.0;
        Double zCoordinate = 300.0;

        SystemEntity entity = SystemEntity.builder()
                .id(id)
                .eliteId(eliteId)
                .name(name)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();

        System result = underTest.map(entity);

        assertThat(result.getId(), is(id));
        assertThat(result.getEliteId(), is(eliteId));
        assertThat(result.getName(), is(name));
        assertThat(result.getXCoordinate(), is(xCoordinate));
        assertThat(result.getYCoordinate(), is(yCoordinate));
        assertThat(result.getZCoordinate(), is(zCoordinate));
    }

    @Test
    void shouldMapModelToEntity() {
        UUID id = UUID.fromString("8a6ce07c-ef95-4b65-81b4-94bac1b827bb");
        Long eliteId = 1001L;
        String name = "Test System";
        Double xCoordinate = 100.0;
        Double yCoordinate = 200.0;
        Double zCoordinate = 300.0;

        System model = System.builder()
                .id(id)
                .eliteId(eliteId)
                .name(name)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();

        SystemEntity result = underTest.map(model);

        assertThat(result.getId(), is(id));
        assertThat(result.getEliteId(), is(eliteId));
        assertThat(result.getName(), is(name));
        assertThat(result.getXCoordinate(), is(xCoordinate));
        assertThat(result.getYCoordinate(), is(yCoordinate));
        assertThat(result.getZCoordinate(), is(zCoordinate));
    }
}
