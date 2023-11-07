package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class KafkaSystemCoordinatesResponseMapperTest {

    private SystemCoordinatesResponseMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new KafkaSystemCoordinatesResponseMapper();
    }

    @Test
    void map_shouldReturnCorrectSystemCoordinatesResponse() {

        String systemName = "test-system";
        Double x = 1.0;
        Double y = 2.0;
        Double z = 3.0;
        Coordinate coordinate = new Coordinate(x, y, z);
        System system = new System(UUID.randomUUID(), null, systemName, null, coordinate);


        SystemCoordinatesResponse result = underTest.map(system);


        assertThat(result.systemName(), is(systemName));
        assertThat(result.xCoordinate(), is(x));
        assertThat(result.yCoordinate(), is(y));
        assertThat(result.zCoordinate(), is(z));
    }
}
