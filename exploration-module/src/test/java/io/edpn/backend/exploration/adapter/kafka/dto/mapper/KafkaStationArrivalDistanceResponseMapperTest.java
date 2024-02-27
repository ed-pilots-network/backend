package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaStationArrivalDistanceResponseMapperTest {

    private KafkaStationArrivalDistanceResponseMapper underTest;

    public static Stream<Arguments> map_shouldReturnCorrectStationArrivalDistanceResponseTestCases() {
        return Stream.of(
                Arguments.of(
                        100.0,
                        100.0
                ),
                Arguments.of(
                        200.0,
                        200.0
                )
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new KafkaStationArrivalDistanceResponseMapper();
    }

    @ParameterizedTest
    @MethodSource("map_shouldReturnCorrectStationArrivalDistanceResponseTestCases")
    void map_shouldReturnCorrectStationArrivalDistanceResponse(Double arrivalDistance, Double expectedArrivalDistance) {
        String systemName = "test-system";
        String stationName = "test-station";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        Station station = mock(Station.class);
        when(station.system()).thenReturn(system);
        when(station.name()).thenReturn(stationName);
        when(station.distanceFromStar()).thenReturn(arrivalDistance);

        StationArrivalDistanceResponse result = underTest.map(station);

        assertThat(result.systemName(), is(systemName));
        assertThat(result.stationName(), is(stationName));
        assertThat(result.arrivalDistance(), is(expectedArrivalDistance));
    }
}