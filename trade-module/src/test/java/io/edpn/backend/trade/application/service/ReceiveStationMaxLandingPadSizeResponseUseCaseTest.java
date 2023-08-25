package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
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
public class ReceiveStationMaxLandingPadSizeResponseUseCaseTest {

    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;

    @Mock
    private DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort;

    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;

    @Mock
    private UpdateStationPort updateStationPort;

    private ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ReceiveStationMaxLandingPadSizeResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, deleteStationLandingPadSizeRequestPort, updateStationPort);
    }

    @Test
    public void shouldReceiveStationMaxLandingPadSizeResponse() {
        StationMaxLandingPadSizeResponse message =
                new StationMaxLandingPadSizeResponse("station", "system", "LARGE");

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
        verify(deleteStationLandingPadSizeRequestPort, times(1)).delete("system", "station");

        assert (station.getMaxLandingPadSize() == LandingPadSize.LARGE);
    }
}
