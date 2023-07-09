package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationPlanetaryResponse;
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
public class ReceiveStationPlanetaryResponseUseCaseTest {

    @Mock
    private SystemRepository systemRepository;

    @Mock
    private StationRepository stationRepository;

    private ReceiveDataRequestResponseUseCase<StationPlanetaryResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ReceiveStationPlanetaryResponseUseCase(systemRepository, stationRepository);
    }

    @Test
    public void shouldReceiveStationPlanetaryResponse() {
        StationPlanetaryResponse message = new StationPlanetaryResponse();
        message.setSystemName("system");
        message.setStationName("station");
        message.setPlanetary(true);

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

        assert(station.getPlanetary());
    }
}
