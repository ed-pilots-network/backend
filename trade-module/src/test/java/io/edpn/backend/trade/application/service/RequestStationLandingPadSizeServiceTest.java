package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.RequestDataService;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestStationLandingPadSizeServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RequestDataMessageRepository requestDataMessageRepository;
    private RequestDataService<Station> underTest;

    public static Stream<Arguments> providePadSizesForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(LandingPadSize.UNKNOWN, true),
                Arguments.of(LandingPadSize.SMALL, false),
                Arguments.of(LandingPadSize.MEDIUM, false),
                Arguments.of(LandingPadSize.LARGE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new RequestStationLandingPadSizeService(requestDataMessageRepository, objectMapper);
    }

    @ParameterizedTest
    @MethodSource("providePadSizesForCheckApplicability")
    void shouldCheckApplicability(LandingPadSize input, boolean expected) {
        Station stationWithPadSize = mock(Station.class);
        when(stationWithPadSize.getMaxLandingPadSize()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithPadSize), is(expected));
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
        assertThat(message.getTopic(), is("stationMaxLandingPadSizeDataRequest"));
        assertThat(message.getMessage(), is(notNullValue()));

        StationDataRequest actualStationDataRequest = objectMapper.treeToValue(message.getMessage(), StationDataRequest.class);
        assertThat(actualStationDataRequest.getStationName(), is(station.getName()));
        assertThat(actualStationDataRequest.getSystemName(), is(system.getName()));
    }
}
