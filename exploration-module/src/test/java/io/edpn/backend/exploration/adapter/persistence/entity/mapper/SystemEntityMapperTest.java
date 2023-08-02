package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.CoordinateEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
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
        // Given
        CoordinateEntity coordinateEntity = new CoordinateEntity(1.0, 2.0, 3.0);
        SystemEntity systemEntity = new SystemEntity(UUID.randomUUID(), "systemName", coordinateEntity, 123L, "starClass");

        // When
        System result = underTest.map(systemEntity);

        // Then
        assertThat(result.id(), equalTo(systemEntity.id()));
        assertThat(result.eliteId(), equalTo(systemEntity.eliteId()));
        assertThat(result.name(), equalTo(systemEntity.name()));
        assertThat(result.starClass(), equalTo(systemEntity.starClass()));
        assertThat(result.coordinate(), notNullValue());
        assertThat(result.coordinate().x(), equalTo(coordinateEntity.x()));
        assertThat(result.coordinate().y(), equalTo(coordinateEntity.y()));
        assertThat(result.coordinate().z(), equalTo(coordinateEntity.z()));
    }

    @Test
    public void testMap_givenSystem_shouldReturnSystemEntity() {
        // Given
        Coordinate coordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "systemName", "starClass", coordinate);

        // When
        SystemEntity result = underTest.map(system);

        // Then
        assertThat(result.id(), equalTo(system.id()));
        assertThat(result.eliteId(), equalTo(system.eliteId()));
        assertThat(result.name(), equalTo(system.name()));
        assertThat(result.starClass(), equalTo(system.starClass()));
        assertThat(result.coordinate(), notNullValue());
        assertThat(result.coordinate().x(), equalTo(coordinate.x()));
        assertThat(result.coordinate().y(), equalTo(coordinate.y()));
        assertThat(result.coordinate().z(), equalTo(coordinate.z()));
    }

    @Test
    public void testMap_givenSystemEntityWithNullCoordinate_shouldReturnSystemWithNullCoordinate() {
        // Given
        SystemEntity systemEntity = new SystemEntity(UUID.randomUUID(), "systemName", null, 123L, "starClass");

        // When
        System result = underTest.map(systemEntity);

        // Then
        assertThat(result.coordinate(), nullValue());
    }

    @Test
    public void testMap_givenSystemWithNullCoordinate_shouldReturnSystemEntityWithNullCoordinate() {
        // Given
        System system = new System(UUID.randomUUID(), 123L, "systemName", "starClass", null);

        // When
        SystemEntity result = underTest.map(system);

        // Then
        assertThat(result.coordinate(), nullValue());
    }
}