package io.edpn.backend.exploration.application.domain.intermodulecommunication;

import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.System;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationMaxLandingPadSizeResponseTest {

    public static Stream<Arguments> map_shouldReturnCorrectStationMaxLandingPadSizeResponseTestCases() {
        return Stream.of(
                Arguments.of(
                        Map.of(
                                LandingPadSize.LARGE, 1,
                                LandingPadSize.MEDIUM, 2,
                                LandingPadSize.SMALL, 3
                        ),
                        LandingPadSize.LARGE.name()
                ),
                Arguments.of(
                        Map.of(
                                LandingPadSize.LARGE, 0,
                                LandingPadSize.MEDIUM, 2,
                                LandingPadSize.SMALL, 3
                        ),
                        LandingPadSize.MEDIUM.name()
                ),
                Arguments.of(
                        Map.of(
                                LandingPadSize.LARGE, 0,
                                LandingPadSize.MEDIUM, 0,
                                LandingPadSize.SMALL, 3
                        ),
                        LandingPadSize.SMALL.name()
                ),
                Arguments.of(
                        Map.of(
                                LandingPadSize.LARGE, 0,
                                LandingPadSize.MEDIUM, 0,
                                LandingPadSize.SMALL, 0
                        ),
                        LandingPadSize.UNKNOWN.name()
                ),
                Arguments.of(
                        Map.of(),
                        LandingPadSize.UNKNOWN.name()
                ),
                Arguments.of(
                        null,
                        LandingPadSize.UNKNOWN.name()
                ));
    }

    @ParameterizedTest
    @MethodSource("map_shouldReturnCorrectStationMaxLandingPadSizeResponseTestCases")
    void map_shouldReturnCorrectStationMaxLandingPadSizeResponse(Map<LandingPadSize, Integer> landingPads, String expectedMaxLandingPadSize) {

        String systemName = "test-system";
        String stationName = "test-station";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        Station station = mock(Station.class);
        when(station.system()).thenReturn(system);
        when(station.name()).thenReturn(stationName);
        when(station.landingPads()).thenReturn(landingPads);

        StationMaxLandingPadSizeResponse result = StationMaxLandingPadSizeResponse.from(station);

        assertThat(result.systemName(), is(systemName));
        assertThat(result.stationName(), is(stationName));
        assertThat(result.maxLandingPadSize(), is(expectedMaxLandingPadSize));
    }
}
