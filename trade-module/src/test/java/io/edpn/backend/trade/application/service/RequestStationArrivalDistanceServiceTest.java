package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestStationArrivalDistanceServiceTest {

    @Mock
    private IdGenerator idGenerator;
    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationArrivalDistanceRequestsPort loadAllStationArrivalDistanceRequestsPort;
    @Mock
    private DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort;
    @Mock
    private CreateOrLoadSystemPort createOrLoadSystemPort;
    @Mock
    private CreateOrLoadStationPort createOrLoadStationPort;
    @Mock
    private ExistsStationArrivalDistanceRequestPort existsStationArrivalDistanceRequestPort;
    @Mock
    private CreateStationArrivalDistanceRequestPort createStationArrivalDistanceRequestPort;
    @Mock
    private UpdateStationPort updateStationPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private Executor executor;
    @Mock
    private ObjectMapper objectMapper;

    private RequestDataUseCase<Station> underTest;

    public static Stream<Arguments> provideDoublesForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(0.0, false),
                Arguments.of(Double.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new StationArrivalDistanceInterModuleCommunicationService(
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationArrivalDistanceRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationArrivalDistanceRequestPort,
                createStationArrivalDistanceRequestPort,
                deleteStationArrivalDistanceRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper);
    }

    @ParameterizedTest
    @MethodSource("provideDoublesForCheckApplicability")
    void shouldCheckApplicability(Double input, boolean expected) {
        Station stationWithArrivalDistance = mock(Station.class);
        when(stationWithArrivalDistance.arrivalDistance()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithArrivalDistance), is(expected));
    }

    @Test
    public void testRequestWhenIdExists() {
        String systemName = "Test System";
        String stationName = "Test Station";

        System system = new System(
                null,
                null,
                systemName,
                null
        );
        Station station = new Station(
                null,
                null,
                stationName,
                null,
                system,
                null,
                null,
                null,
                null,
                null,
                null
        );

        when(existsStationArrivalDistanceRequestPort.exists(systemName, stationName)).thenReturn(true);

        underTest.request(station);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createStationArrivalDistanceRequestPort, never()).create(anyString(), anyString());
    }

    @Test
    public void testRequestWhenIdDoesNotExist() {
        String systemName = "Test System";
        String stationName = "Test Station";

        System system = new System(
                null,
                null,
                systemName,
                null
        );
        Station station = new Station(
                null,
                null,
                stationName,
                null,
                system,
                null,
                null,
                null,
                null,
                null,
                null
        );

        JsonNode mockJsonNode = mock(JsonNode.class);
        String mockJsonString = "jsonString";
        Message message = new Message(Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName(), "jsonString");

        when(existsStationArrivalDistanceRequestPort.exists(systemName, stationName)).thenReturn(false);
        when(objectMapper.valueToTree(argThat(arg -> {
            if (arg instanceof StationDataRequest stationDataRequest) {
                return systemName.equals(stationDataRequest.systemName()) && stationName.equals(stationDataRequest.stationName()) && Module.TRADE.equals(stationDataRequest.requestingModule());
            } else {
                return false;
            }
        }))).thenReturn(mockJsonNode);
        when(mockJsonNode.toString()).thenReturn(mockJsonString);

        underTest.request(station);

        verify(sendKafkaMessagePort).send(message);
        verify(createStationArrivalDistanceRequestPort).create(systemName, stationName);
    }
}
