package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiveStationArrivalDistanceResponseUseCaseTest {

    @Mock
    private SystemRepository systemRepository;

    @Mock
    private StationRepository stationRepository;

    private ReceiveDataRequestResponseUseCase<StationArrivalDistanceResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ReceiveStationArrivalDistanceResponseUseCase(systemRepository, stationRepository);
    }

    @Test
    public void shouldReceiveStationArrivalDistanceResponse() {
        StationArrivalDistanceResponse message = new StationArrivalDistanceResponse();
        message.setSystemName("system");
        message.setStationName("station");
        message.setArrivalDistance(1.0);

        System system = mock(System.class);
        when(systemRepository.findOrCreateByName("system")).thenReturn(system);

        Station station = mock(Station.class);
        when(stationRepository.findOrCreateBySystemAndStationName(system, "station")).thenReturn(station);

        underTest.receive(message);

        verify(systemRepository, times(1)).findOrCreateByName(anyString());
        verify(stationRepository, times(1)).findOrCreateBySystemAndStationName(any(), anyString());
        verify(stationRepository, times(1)).update(any());
    }
}
