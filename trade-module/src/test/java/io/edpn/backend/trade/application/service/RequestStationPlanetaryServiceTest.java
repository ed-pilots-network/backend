package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.service.SendDataRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestStationPlanetaryServiceTest {

    @Mock
    private SendDataRequestService<StationDataRequest> stationDataRequestSendDataRequestService;
    private RequestDataService<Station> underTest;

    public static Stream<Arguments> provideBooleansForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(Boolean.TRUE, false),
                Arguments.of(Boolean.FALSE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new RequestStationPlanetaryService(stationDataRequestSendDataRequestService);
    }

    @ParameterizedTest
    @MethodSource("provideBooleansForCheckApplicability")
    void shouldCheckApplicability(Boolean input, boolean expected) {
        Station stationWithPlanetary = mock(Station.class);
        when(stationWithPlanetary.getPlanetary()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithPlanetary), is(expected));
    }

    @Test
    void shouldSendRequest() {
        System system = System.builder()
                .name("Test System")
                .build();
        Station station = Station.builder()
                .name("Test Station")
                .system(system)
                .build();

        underTest.request(station);

        ArgumentCaptor<StationDataRequest> stationDataRequestArgumentCaptor = ArgumentCaptor.forClass(StationDataRequest.class);
        ArgumentCaptor<String> topicArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(stationDataRequestSendDataRequestService, times(1)).send(stationDataRequestArgumentCaptor.capture(), topicArgumentCaptor.capture());

        StationDataRequest actualSystemDataRequest = stationDataRequestArgumentCaptor.getValue();
        String actualTopic = topicArgumentCaptor.getValue();
        assertThat(actualSystemDataRequest.getSystemName(), is(system.getName()));
        assertThat(actualSystemDataRequest.getStationName(), is(station.getName()));
        assertThat(actualSystemDataRequest.getRequestingModule(), is("trade"));
        assertThat(actualTopic, equalTo("stationPlanetaryDataRequest"));
    }
}
