package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.SystemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class SystemEntityMapperTest {

    private io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemEntityMapper();
    }

    @Test
    public void testMap_givenSystemEntity_shouldReturnSystem() {
        SystemEntity systemEntity = new io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity(UUID.randomUUID(), "systemName", 123L, "starClass", 1.0, 2.0, 3.0);

        System result = underTest.map(systemEntity);

        assertThat(result.id(), equalTo(systemEntity.getId()));
        assertThat(result.eliteId(), equalTo(systemEntity.getEliteId()));
        assertThat(result.name(), equalTo(systemEntity.getName()));
        assertThat(result.starClass(), equalTo(systemEntity.getStarClass()));
        assertThat(result.coordinate(), notNullValue());
        assertThat(result.coordinate().x(), equalTo(systemEntity.getXCoordinate()));
        assertThat(result.coordinate().y(), equalTo(systemEntity.getYCoordinate()));
        assertThat(result.coordinate().z(), equalTo(systemEntity.getZCoordinate()));
    }

    @Test
    public void testMap_givenSystem_shouldReturnSystemEntity() {

        Coordinate coordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "systemName", "starClass", coordinate);


        SystemEntity result = underTest.map(system);


        assertThat(result.getId(), equalTo(system.id()));
        assertThat(result.getEliteId(), equalTo(system.eliteId()));
        assertThat(result.getName(), equalTo(system.name()));
        assertThat(result.getStarClass(), equalTo(system.starClass()));
        assertThat(result.getXCoordinate(), equalTo(coordinate.x()));
        assertThat(result.getYCoordinate(), equalTo(coordinate.y()));
        assertThat(result.getZCoordinate(), equalTo(coordinate.z()));
    }

    @Test
    public void testMap_givenSystemEntityWithNullCoordinate_shouldReturnSystemWithNullCoordinate() {

        SystemEntity systemEntity = new io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity(UUID.randomUUID(), "systemName", 123L, "starClass", null, null, null);


        System result = underTest.map(systemEntity);


        assertThat(result.coordinate(), notNullValue());
        assertThat(result.coordinate().x(), nullValue());
        assertThat(result.coordinate().y(), nullValue());
        assertThat(result.coordinate().z(), nullValue());
    }

    @Test
    public void testMap_givenSystemWithNullCoordinate_shouldReturnSystemEntityWithNullCoordinate() {

        System system = new System(UUID.randomUUID(), 123L, "systemName", "starClass", null);


        SystemEntity result = underTest.map(system);


        assertThat(result.getXCoordinate(), nullValue());
        assertThat(result.getYCoordinate(), nullValue());
        assertThat(result.getZCoordinate(), nullValue());
    }
}