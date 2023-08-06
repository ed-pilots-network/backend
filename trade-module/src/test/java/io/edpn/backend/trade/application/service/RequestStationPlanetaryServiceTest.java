package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.RequestDataService;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestStationPlanetaryServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RequestDataMessageRepository requestDataMessageRepository;
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
        underTest = new RequestStationPlanetaryService(requestDataMessageRepository, objectMapper);
    }

    @ParameterizedTest
    @MethodSource("provideBooleansForCheckApplicability")
    void shouldCheckApplicability(Boolean input, boolean expected) {
        Station stationWithPlanetary = mock(Station.class);
        when(stationWithPlanetary.getPlanetary()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithPlanetary), is(expected));
    }

    @Test
    void shouldSendRequest() throws Exception {
        System system = System.builder()
                .name("Test System")
                .build();
        Station station = Station.builder()
                .name("Test Station")
                .system(system)
                .build();

        underTest.request(station);

        ArgumentCaptor<RequestDataMessage> argumentCaptor = ArgumentCaptor.forClass(RequestDataMessage.class);
        verify(requestDataMessageRepository, times(1)).sendToKafka(argumentCaptor.capture());

        RequestDataMessage message = argumentCaptor.getValue();
        assertThat(message, is(notNullValue()));
        assertThat(message.getTopic(), is("tradeModuleStationPlanetaryDataRequest"));
        assertThat(message.getMessage(), is(notNullValue()));

        StationDataRequest actualStationDataRequest = objectMapper.treeToValue(message.getMessage(), StationDataRequest.class);
        assertThat(actualStationDataRequest.stationName(), is(station.getName()));
        assertThat(actualStationDataRequest.systemName(), is(system.getName()));
    }
}
