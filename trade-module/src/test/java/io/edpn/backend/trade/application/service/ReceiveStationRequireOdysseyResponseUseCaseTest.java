package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationRequireOdysseyResponse;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;

import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveStationRequireOdysseyResponseUseCaseTest {

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
    private ObjectMapper objectMapper;
    @Mock
    private MessageMapper messageMapper;

    private ReceiveKafkaMessageUseCase<StationRequireOdysseyResponse> underTest;

    @BeforeEach
    public void setUp() {
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

    @Test
    public void shouldReceiveStationRequireOdysseyResponse() {
        StationRequireOdysseyResponse message =
                new StationRequireOdysseyResponse("station", "system", true);

        System system = mock(System.class);
        when(loadOrCreateSystemByNamePort.loadOrCreateSystemByName("system")).thenReturn(system);

        Station station = Station.builder()
                .name("station")
                .build();
        when(loadOrCreateBySystemAndStationNamePort.loadOrCreateBySystemAndStationName(system, "station")).thenReturn(station);

        underTest.receive(message);

        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(anyString());
        verify(loadOrCreateBySystemAndStationNamePort, times(1)).loadOrCreateBySystemAndStationName(any(), anyString());
        verify(updateStationPort, times(1)).update(any());
        verify(deleteStationRequireOdysseyRequestPort, times(1)).delete("system", "station");

        assert (station.getRequireOdyssey());
    }
}
