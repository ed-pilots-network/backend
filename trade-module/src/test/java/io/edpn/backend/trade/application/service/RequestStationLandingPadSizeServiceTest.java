package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.MessageDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestStationLandingPadSizeServiceTest {

    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ExistsStationLandingPadSizeRequestPort existsStationLandingPadSizeRequestPort;
    @Mock
    private CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort;

    @Mock
    private MessageMapper messageMapper;

    private RequestDataUseCase<Station> underTest;

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
        underTest = new RequestStationLandingPadSizeService(sendKafkaMessagePort, existsStationLandingPadSizeRequestPort, createStationLandingPadSizeRequestPort, objectMapper, messageMapper);
    }

    @ParameterizedTest
    @MethodSource("providePadSizesForCheckApplicability")
    void shouldCheckApplicability(LandingPadSize input, boolean expected) {
        Station stationWithPadSize = mock(Station.class);
        when(stationWithPadSize.getMaxLandingPadSize()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithPadSize), is(expected));
    }


    @Test
    public void testRequestWhenIdExists() {
        String systemName = "Test System";
        String stationName = "Test Station";
        System system = System.builder()
                .name(systemName)
                .build();
        Station station = Station.builder()
                .name(stationName)
                .system(system)
                .build();

        when(existsStationLandingPadSizeRequestPort.exists(systemName, stationName)).thenReturn(true);

        underTest.request(station);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createStationLandingPadSizeRequestPort, never()).create(anyString(), anyString());
    }

    @Test
    public void testRequestWhenIdDoesNotExist() {
        String systemName = "Test System";
        String stationName = "Test Station";

        System system = System.builder()
                .name(systemName)
                .build();
        Station station = Station.builder()
                .name(stationName)
                .system(system)
                .build();

        JsonNode mockJsonNode = mock(JsonNode.class);
        String mockJsonString = "jsonString";
        MessageDto mockMessageDto = mock(MessageDto.class);

        when(existsStationLandingPadSizeRequestPort.exists(systemName, stationName)).thenReturn(false);
        when(objectMapper.valueToTree(argThat(arg -> {
            if (arg instanceof StationDataRequest stationDataRequest) {
                return systemName.equals(stationDataRequest.systemName()) && stationName.equals(stationDataRequest.stationName()) && Module.TRADE.equals(stationDataRequest.requestingModule());
            } else {
                return false;
            }
        }))).thenReturn(mockJsonNode);
        when(mockJsonNode.toString()).thenReturn(mockJsonString);
        when(messageMapper.map(argThat(argument ->
                argument.getMessage().equals(mockJsonString) && argument.getTopic().equals(Topic.Request.STATION_MAX_LANDING_PAD_SIZE.getTopicName())
        ))).thenReturn(mockMessageDto);

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);


        underTest.request(station);

        verify(sendKafkaMessagePort).send(mockMessageDto);
        verify(createStationLandingPadSizeRequestPort).create(systemName, stationName);
        verify(messageMapper, times(1)).map(argumentCaptor.capture());
        Message message = argumentCaptor.getValue();
        assertThat(message, is(notNullValue()));
        assertThat(message.getTopic(), is(Topic.Request.STATION_MAX_LANDING_PAD_SIZE.getTopicName()));
        assertThat(message.getMessage(), is("jsonString"));
    }
}
