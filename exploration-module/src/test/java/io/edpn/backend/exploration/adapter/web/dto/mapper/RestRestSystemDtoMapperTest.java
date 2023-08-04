package io.edpn.backend.exploration.adapter.web.dto.mapper;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.SystemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class RestRestSystemDtoMapperTest {

    private io.edpn.backend.exploration.application.dto.mapper.SystemDtoMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new RestSystemDtoMapper();
    }

    @Test
    void testMap_systemWithCoordinate_shouldReturnMappedSystemDto() {
        
        UUID id = UUID.fromString("b85e6a0c-bc0f-447e-812e-9adf3b63cda9");
        String name = "system";
        Long eliteId = 123L;
        String starClass = "class";
        Double x = 1.0;
        Double y = 2.0;
        Double z = 3.0;
        Coordinate coordinate = new Coordinate(x, y, z);
        System system = new System(id, eliteId, name, starClass, coordinate);


        SystemDto result = underTest.map(system);


        assertThat(result.name(), equalTo(name));
        assertThat(result.eliteId(), equalTo(eliteId));
        assertThat(result.starClass(), equalTo(starClass));
        assertThat(result.coordinate(), notNullValue());
        assertThat(result.coordinate().x(), equalTo(x));
        assertThat(result.coordinate().y(), equalTo(y));
        assertThat(result.coordinate().z(), equalTo(z));
    }

    @Test
    void testMap_systemWithoutCoordinate_shouldReturnMappedSystemDtoWithNullCoordinate() {
        
        UUID id = UUID.fromString("b85e6a0c-bc0f-447e-812e-9adf3b63cda9");
        String name = "system";
        Long eliteId = 123L;
        String starClass = "class";
        System system = new System(id, eliteId, name, starClass, null);


        SystemDto result = underTest.map(system);


        assertThat(result.name(), equalTo(name));
        assertThat(result.eliteId(), equalTo(eliteId));
        assertThat(result.starClass(), equalTo(starClass));
        assertThat(result.coordinate(), nullValue());
    }
}