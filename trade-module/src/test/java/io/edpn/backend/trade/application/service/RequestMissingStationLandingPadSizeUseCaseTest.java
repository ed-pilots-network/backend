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
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.RequestMissingStationLandingPadSizeUseCase;
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
public class RequestMissingStationLandingPadSizeUseCaseTest {
    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    @Mock
    private ExistsStationLandingPadSizeRequestPort existsStationLandingPadSizeRequestPort;
    @Mock
    private CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort;
    @Mock
    private DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort;
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

    private RequestMissingStationLandingPadSizeUseCase underTest;

    private final Executor executor = Runnable::run;

    @BeforeEach
    public void setUp() {
        underTest = new StationLandingPadSizeInterModuleCommunicationService(
                loadStationsByFilterPort,
                loadAllStationLandingPadSizeRequestsPort,
                loadOrCreateSystemByNamePort,
                loadOrCreateBySystemAndStationNamePort,
                existsStationLandingPadSizeRequestPort,
                createStationLandingPadSizeRequestPort,
                deleteStationLandingPadSizeRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Test
    public void testFindSystemsFilter() {
        FindStationFilter findSystemFilter = FindStationFilter.builder()
                .hasLandingPadSize(false)
                .build();

        assertThat(StationLandingPadSizeInterModuleCommunicationService.FIND_STATION_FILTER, equalTo(findSystemFilter));
    }

    @Test
    public void testRequestMissingForZeroResults() {
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        underTest.requestMissing();

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createStationLandingPadSizeRequestPort, never()).create(any(), any());
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
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("stationMaxLandingPadSizeRequest") && argument.getMessage().equals("jsonNodeString")))).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.requestMissing();

        verify(sendKafkaMessagePort).send(any());
        verify(createStationLandingPadSizeRequestPort).create(any(), any());
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
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("stationMaxLandingPadSizeRequest") && argument.getMessage().equals("jsonNodeString1")))).thenReturn(messageDto1);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("stationMaxLandingPadSizeRequest") && argument.getMessage().equals("jsonNodeString2")))).thenReturn(messageDto2);
        when(sendKafkaMessagePort.send(messageDto1)).thenReturn(true);
        when(sendKafkaMessagePort.send(messageDto2)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.requestMissing();

        verify(sendKafkaMessagePort, times(2)).send(any());
        verify(createStationLandingPadSizeRequestPort, times(2)).create(any(), any());
    }

}