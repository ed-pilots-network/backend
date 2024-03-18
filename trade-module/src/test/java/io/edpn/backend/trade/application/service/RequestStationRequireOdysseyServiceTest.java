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
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateIfNotExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;
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
public class RequestStationRequireOdysseyServiceTest {

    @Mock
    private IdGenerator idGenerator;
    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationRequireOdysseyRequestsPort loadAllStationRequireOdysseyRequestsPort;
    @Mock
    private CreateOrLoadSystemPort createOrLoadSystemPort;
    @Mock
    private CreateOrLoadStationPort createOrLoadStationPort;
    @Mock
    private ExistsStationRequireOdysseyRequestPort existsStationRequireOdysseyRequestPort;
    @Mock
    private CreateIfNotExistsStationRequireOdysseyRequestPort createIfNotExistsStationRequireOdysseyRequestPort;
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
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationRequireOdysseyRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationRequireOdysseyRequestPort,
                createIfNotExistsStationRequireOdysseyRequestPort,
                deleteStationRequireOdysseyRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper
        );
    }

    @ParameterizedTest
    @MethodSource("provideBooleansForCheckApplicability")
    void shouldCheckApplicability(Boolean input, boolean expected) {
        Station stationWithOdysseyRequirement = mock(Station.class);
        when(stationWithOdysseyRequirement.requireOdyssey()).thenReturn(input);

        assertThat(underTest.isApplicable(stationWithOdysseyRequirement), is(expected));
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

        when(existsStationRequireOdysseyRequestPort.exists(systemName, stationName)).thenReturn(true);

        underTest.request(station);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createIfNotExistsStationRequireOdysseyRequestPort, never()).createIfNotExists(anyString(), anyString());
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
        Message message = new Message(Topic.Request.STATION_REQUIRE_ODYSSEY.getTopicName(), "jsonString");

        when(existsStationRequireOdysseyRequestPort.exists(systemName, stationName)).thenReturn(false);
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
        verify(createIfNotExistsStationRequireOdysseyRequestPort).createIfNotExists(systemName, stationName);
    }
}
