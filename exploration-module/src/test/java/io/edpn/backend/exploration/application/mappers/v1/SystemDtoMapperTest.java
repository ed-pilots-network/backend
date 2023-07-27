package io.edpn.backend.exploration.application.mappers.v1;

import io.edpn.backend.exploration.domain.dto.v1.SystemDto;
import io.edpn.backend.exploration.domain.model.System;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class SystemDtoMapperTest {

    private final SystemDtoMapper underTest = new SystemDtoMapper();

    @Test
    void shouldMapSystemToSystemDTOWithCoordinates() {
        System system = System.builder()
                .name("Test System")
                .eliteId(1L)
                .starClass("A")
                .xCoordinate(2.0)
                .yCoordinate(3.0)
                .zCoordinate(4.0)
                .build();

        SystemDto result = underTest.map(system);

        assertThat(system.getName(), equalTo(result.name()));
        assertThat(system.getEliteId(), equalTo(result.eliteId()));
        assertThat(system.getStarClass(), equalTo(result.starClass()));
        assertThat(system.getXCoordinate(), equalTo(result.coordinates().x()));
        assertThat(system.getYCoordinate(), equalTo(result.coordinates().y()));
        assertThat(system.getZCoordinate(), equalTo(result.coordinates().z()));
    }

    @Test
    void shouldMapSystemToSystemDTOWithoutCoordinates() {
        System system = System.builder()
                .name("Test System")
                .eliteId(1L)
                .build();

        SystemDto result = underTest.map(system);

        assertThat(system.getName(), equalTo(result.name()));
        assertThat(system.getEliteId(), equalTo(result.eliteId()));
        assertThat(result.coordinates(), is(nullValue()));
    }
}
