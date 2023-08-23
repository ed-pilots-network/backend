package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationPlanetaryResponse;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveStationPlanetaryResponseUseCaseTest {
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    
    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    
    @Mock
    private UpdateStationPort updateStationPort;
    
    private ReceiveKafkaMessageUseCase<StationPlanetaryResponse> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new ReceiveStationPlanetaryResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, updateStationPort);
    }

    @Test
    public void shouldReceiveStationPlanetaryResponse() {
        StationPlanetaryResponse message = new StationPlanetaryResponse();
        message.setSystemName("system");
        message.setStationName("station");
        message.setPlanetary(true);

        System system = mock(System.class);
        when(loadOrCreateSystemByNamePort.loadOrCreateSystemByName(anyString())).thenReturn(system);

        Station station = Station.builder()
                .name("station")
                .build();
        when(loadOrCreateBySystemAndStationNamePort.loadOrCreateBySystemAndStationName(system, "station")).thenReturn(station);

        underTest.receive(message);

        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(anyString());
        verify(loadOrCreateBySystemAndStationNamePort, times(1)).loadOrCreateBySystemAndStationName(any(), anyString());
        verify(updateStationPort, times(1)).update(any());

        assert (station.getPlanetary());
    }
}
