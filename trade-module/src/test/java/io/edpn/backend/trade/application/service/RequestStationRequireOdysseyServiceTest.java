package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.MessageDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import java.util.concurrent.Executor;
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
import org.springframework.retry.support.RetryTemplate;

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
public class RequestStationRequireOdysseyServiceTest {

    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationRequireOdysseyRequestsPort loadAllStationRequireOdysseyRequestsPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    @Mock
    private ExistsStationRequireOdysseyRequestPort existsStationRequireOdysseyRequestPort;
    @Mock
    private CreateStationRequireOdysseyRequestPort createStationRequireOdysseyRequestPort;
    @Mock
    private DeleteStationRequireOdysseyRequestPort deleteStationRequireOdysseyRequestPort;
    @Mock
    private UpdateStationPort updateStationPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private Executor executor;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;

    private RequestDataUseCase<Station> underTest;

    public static Stream<Arguments> provideBooleansForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(Boolean.TRUE, false),
                Arguments.of(Boolean.FALSE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new StationRequireOdysseyInterModuleCommunicationService(
                loadStationsByFilterPort,
                loadAllStationRequireOdysseyRequestsPort,
                loadOrCreateSystemByNamePort,
                loadOrCreateBySystemAndStationNamePort,
                existsStationRequireOdysseyRequestPort,
                createStationRequireOdysseyRequestPort,
                deleteStationRequireOdysseyRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @ParameterizedTest
    @MethodSource("provideBooleansForCheckApplicability")
    void shouldCheckApplicability(Boolean input, boolean expected) {
        Station stationWithOdysseyRequirement = mock(Station.class);
        when(stationWithOdysseyRequirement.getRequireOdyssey()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithOdysseyRequirement), is(expected));
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

        when(existsStationRequireOdysseyRequestPort.exists(systemName, stationName)).thenReturn(true);

        underTest.request(station);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createStationRequireOdysseyRequestPort, never()).create(anyString(), anyString());
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

        when(existsStationRequireOdysseyRequestPort.exists(systemName, stationName)).thenReturn(false);
        when(objectMapper.valueToTree(argThat(arg -> {
            if (arg instanceof StationDataRequest stationDataRequest) {
                return systemName.equals(stationDataRequest.systemName()) && stationName.equals(stationDataRequest.stationName()) && Module.TRADE.equals(stationDataRequest.requestingModule());
            } else {
                return false;
            }
        }))).thenReturn(mockJsonNode);
        when(mockJsonNode.toString()).thenReturn(mockJsonString);
        when(messageMapper.map(argThat(argument ->
                argument.getMessage().equals(mockJsonString) && argument.getTopic().equals(Topic.Request.STATION_REQUIRE_ODYSSEY.getTopicName())
        ))).thenReturn(mockMessageDto);

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);


        underTest.request(station);

        verify(sendKafkaMessagePort).send(mockMessageDto);
        verify(createStationRequireOdysseyRequestPort).create(systemName, stationName);
        verify(messageMapper, times(1)).map(argumentCaptor.capture());
        Message message = argumentCaptor.getValue();
        assertThat(message, is(notNullValue()));
        assertThat(message.getTopic(), is(Topic.Request.STATION_REQUIRE_ODYSSEY.getTopicName()));
        assertThat(message.getMessage(), is("jsonString"));
    }
}
