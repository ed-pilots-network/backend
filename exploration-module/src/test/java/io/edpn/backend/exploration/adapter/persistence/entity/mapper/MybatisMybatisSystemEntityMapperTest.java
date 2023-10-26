package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class MybatisMybatisSystemEntityMapperTest {

    private SystemEntityMapper<MybatisSystemEntity> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisSystemEntityMapper();
    }

    @Test
    public void testMap_givenSystemEntity_shouldReturnSystem() {
        SystemEntity systemEntity = new MybatisSystemEntity(UUID.randomUUID(), "systemName", 123L, "starClass", 1.0, 2.0, 3.0);

        System result = underTest.map(systemEntity);

        assertThat(result.getId(), equalTo(systemEntity.getId()));
        assertThat(result.getEliteId(), equalTo(systemEntity.getEliteId()));
        assertThat(result.getName(), equalTo(systemEntity.getName()));
        assertThat(result.getStarClass(), equalTo(systemEntity.getStarClass()));
        assertThat(result.getCoordinate(), notNullValue());
        assertThat(result.getCoordinate().getX(), equalTo(systemEntity.getXCoordinate()));
        assertThat(result.getCoordinate().getY(), equalTo(systemEntity.getYCoordinate()));
        assertThat(result.getCoordinate().getZ(), equalTo(systemEntity.getZCoordinate()));
    }

    @Test
    public void testMap_givenSystem_shouldReturnSystemEntity() {

        Coordinate coordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "systemName", "starClass", coordinate);


        SystemEntity result = underTest.map(system);


        assertThat(result.getId(), equalTo(system.getId()));
        assertThat(result.getEliteId(), equalTo(system.getEliteId()));
        assertThat(result.getName(), equalTo(system.getName()));
        assertThat(result.getStarClass(), equalTo(system.getStarClass()));
        assertThat(result.getXCoordinate(), equalTo(coordinate.getX()));
        assertThat(result.getYCoordinate(), equalTo(coordinate.getY()));
        assertThat(result.getZCoordinate(), equalTo(coordinate.getZ()));
    }

    @Test
    public void testMap_givenSystemEntityWithNullCoordinate_shouldReturnSystemWithNullCoordinate() {

        SystemEntity systemEntity = new MybatisSystemEntity(UUID.randomUUID(), "systemName", 123L, "starClass", null, null, null);


        System result = underTest.map(systemEntity);


        assertThat(result.getCoordinate(), notNullValue());
        assertThat(result.getCoordinate().getX(), nullValue());
        assertThat(result.getCoordinate().getY(), nullValue());
        assertThat(result.getCoordinate().getZ(), nullValue());
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