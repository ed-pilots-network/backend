package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RequestStationArrivalDistanceServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RequestDataMessageRepository requestDataMessageRepository;
    private RequestStationArrivalDistanceService underTest;

    public static Stream<Arguments> provideDoublesForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(0.0, false),
                Arguments.of(Double.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new RequestStationArrivalDistanceService(requestDataMessageRepository, objectMapper);
    }

    @ParameterizedTest
    @MethodSource("provideDoublesForCheckApplicability")
    void shouldCheckApplicability(Double input, boolean expected) {
        Station stationWithArrivalDistance = new Station();
        stationWithArrivalDistance.setArrivalDistance(input);

        assertThat(underTest.isApplicable(stationWithArrivalDistance), is(expected));
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
        assertThat(message.getTopic(), is("tradeModuleStationArrivalDistanceDataRequest"));
        assertThat(message.getMessage(), is(notNullValue()));

        StationDataRequest actualStationDataRequest = objectMapper.treeToValue(message.getMessage(), StationDataRequest.class);
        assertThat(actualStationDataRequest.getStationName(), is(station.getName()));
        assertThat(actualStationDataRequest.getSystemName(), is(system.getName()));
    }
}
