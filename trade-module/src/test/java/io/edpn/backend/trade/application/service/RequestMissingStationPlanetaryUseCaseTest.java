package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.web.object.MessageDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.LoadAllStationPlanetaryRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.RequestMissingStationPlanetaryUseCase;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.util.Module;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestMissingStationPlanetaryUseCaseTest {
    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationPlanetaryRequestsPort loadAllStationPlanetaryRequestsPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    @Mock
    private ExistsStationPlanetaryRequestPort existsStationPlanetaryRequestPort;
    @Mock
    private CreateStationPlanetaryRequestPort createStationPlanetaryRequestPort;
    @Mock
    private DeleteStationPlanetaryRequestPort deleteStationPlanetaryRequestPort;
    @Mock
    private UpdateStationPort updateStationPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private MessageMapper messageMapper;

    private RequestMissingStationPlanetaryUseCase underTest;

    private final Executor executor = Runnable::run;

    @BeforeEach
    public void setUp() {
        underTest = new StationPlanetaryInterModuleCommunicationService(
                loadStationsByFilterPort,
                loadAllStationPlanetaryRequestsPort,
                loadOrCreateSystemByNamePort,
                loadOrCreateBySystemAndStationNamePort,
                existsStationPlanetaryRequestPort,
                createStationPlanetaryRequestPort,
                deleteStationPlanetaryRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Test
    public void testFindStationsFilter() {
        FindStationFilter findStationFilter = FindStationFilter.builder()
                .hasPlanetary(false)
                .build();

        assertThat(StationPlanetaryInterModuleCommunicationService.FIND_STATION_FILTER, equalTo(findStationFilter));
    }

    @Test
    public void testRequestMissingForZeroResults() {
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        underTest.requestMissing();

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createStationPlanetaryRequestPort, never()).create(any(), any());
    }

    @Test
    public void testRequestMissingForOneResult() {
        System system = mock(System.class);
        when(system.getName()).thenReturn("Alpha");
        Station station = mock(Station.class);
        when(station.getName()).thenReturn("home");
        when(station.getSystem()).thenReturn(system);
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(List.of(station));
        JsonNode jsonNode = mock(JsonNode.class);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof StationDataRequest stationDataRequest) {
                return Module.TRADE == stationDataRequest.requestingModule() && stationDataRequest.stationName().equals("home") && stationDataRequest.systemName().equals("Alpha");
            } else {
                return false;
            }
        }))).thenReturn(jsonNode);
        when(jsonNode.toString()).thenReturn("jsonNodeString");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("stationIsPlanetaryRequest") && argument.getMessage().equals("jsonNodeString")))).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.requestMissing();

        verify(sendKafkaMessagePort).send(any());
        verify(createStationPlanetaryRequestPort).create(any(), any());
    }

    @Test
    public void testRequestMissingForMultipleResult() {
        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Alpha");
        Station station1 = mock(Station.class);
        when(station1.getName()).thenReturn("home");
        when(station1.getSystem()).thenReturn(system1);
        System system2 = mock(System.class);
        when(system2.getName()).thenReturn("Bravo");
        Station station2 = mock(Station.class);
        when(station2.getName()).thenReturn("away");
        when(station2.getSystem()).thenReturn(system2);
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(List.of(station1, station2));
        JsonNode jsonNode1 = mock(JsonNode.class);
        JsonNode jsonNode2 = mock(JsonNode.class);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof StationDataRequest stationDataRequest) {
                return Module.TRADE == stationDataRequest.requestingModule() && stationDataRequest.stationName().equals("home") && stationDataRequest.systemName().equals("Alpha");
            } else {
                return false;
            }
        }))).thenReturn(jsonNode1);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof StationDataRequest stationDataRequest) {
                return Module.TRADE == stationDataRequest.requestingModule() && stationDataRequest.stationName().equals("away") && stationDataRequest.systemName().equals("Bravo");
            } else {
                return false;
            }
        }))).thenReturn(jsonNode2);
        when(jsonNode1.toString()).thenReturn("jsonNodeString1");
        when(jsonNode2.toString()).thenReturn("jsonNodeString2");
        MessageDto messageDto1 = mock(MessageDto.class);
        MessageDto messageDto2 = mock(MessageDto.class);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("stationIsPlanetaryRequest") && argument.getMessage().equals("jsonNodeString1")))).thenReturn(messageDto1);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("stationIsPlanetaryRequest") && argument.getMessage().equals("jsonNodeString2")))).thenReturn(messageDto2);
        when(sendKafkaMessagePort.send(messageDto1)).thenReturn(true);
        when(sendKafkaMessagePort.send(messageDto2)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.requestMissing();

        verify(sendKafkaMessagePort, times(2)).send(any());
        verify(createStationPlanetaryRequestPort, times(2)).create(any(), any());
    }

}