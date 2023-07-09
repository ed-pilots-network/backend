package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationRequireOdysseyResponse;
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
public class ReceiveStationRequireOdysseyResponseUseCaseTest {

    @Mock
    private SystemRepository systemRepository;

    @Mock
    private StationRepository stationRepository;

    private ReceiveDataRequestResponseUseCase<StationRequireOdysseyResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ReceiveStationRequireOdysseyResponseUseCase(systemRepository, stationRepository);
    }

    @Test
    public void shouldReceiveStationRequireOdysseyResponse() {
        StationRequireOdysseyResponse message = new StationRequireOdysseyResponse();
        message.setSystemName("system");
        message.setStationName("station");
        message.setRequireOdyssey(true);

        System system = new System();
        system.setName("system");
        when(systemRepository.findOrCreateByName(anyString())).thenReturn(system);

        Station station = new Station();
        station.setName("station");
        when(stationRepository.findOrCreateBySystemAndStationName(any(), anyString())).thenReturn(station);

        underTest.receive(message);

        verify(systemRepository, times(1)).findOrCreateByName(anyString());
        verify(stationRepository, times(1)).findOrCreateBySystemAndStationName(any(), anyString());
        verify(stationRepository, times(1)).update(any());

        assert(station.getRequireOdyssey());
    }
}
