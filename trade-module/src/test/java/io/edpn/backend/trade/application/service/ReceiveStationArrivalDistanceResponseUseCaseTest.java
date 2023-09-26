package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
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
public class ReceiveStationArrivalDistanceResponseUseCaseTest {

    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationArrivalDistanceRequestsPort loadAllStationArrivalDistanceRequestsPort;
    @Mock
    private DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
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
    @Mock
    private MessageMapper messageMapper;

    private ReceiveKafkaMessageUseCase<StationArrivalDistanceResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationArrivalDistanceInterModuleCommunicationService(
                loadStationsByFilterPort,
                loadAllStationArrivalDistanceRequestsPort,
                loadOrCreateSystemByNamePort,
                loadOrCreateBySystemAndStationNamePort,
                existsStationArrivalDistanceRequestPort,
                createStationArrivalDistanceRequestPort,
                deleteStationArrivalDistanceRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper);
    }

    @Test
    public void shouldReceiveStationArrivalDistanceResponse() {
        StationArrivalDistanceResponse message =
                new StationArrivalDistanceResponse("station", "system", 1.0);


        System system = mock(System.class);
        when(loadOrCreateSystemByNamePort.loadOrCreateSystemByName("system")).thenReturn(system);

        Station station = mock(Station.class);
        when(loadOrCreateBySystemAndStationNamePort.loadOrCreateBySystemAndStationName(system, "station")).thenReturn(station);

        underTest.receive(message);

        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(anyString());
        verify(loadOrCreateBySystemAndStationNamePort, times(1)).loadOrCreateBySystemAndStationName(any(), anyString());
        verify(updateStationPort, times(1)).update(any());
        verify(deleteStationArrivalDistanceRequestPort, times(1)).delete("system", "station");

    }
}
