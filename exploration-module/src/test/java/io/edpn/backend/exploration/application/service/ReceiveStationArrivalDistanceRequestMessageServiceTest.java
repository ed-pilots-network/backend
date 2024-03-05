package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.CreateIfNotExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.StationArrivalDistanceResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveStationArrivalDistanceRequestMessageServiceTest {

    @Mock
    private LoadAllStationArrivalDistanceRequestPort loadAllStationArrivalDistanceRequestPort;
    @Mock
    private CreateIfNotExistsStationArrivalDistanceRequestPort createIfNotExistsStationArrivalDistanceRequestPort;
    @Mock
    private LoadStationPort loadStationPort;
    @Mock
    private StationArrivalDistanceResponseSender stationArrivalDistanceResponseSender;
    @Mock
    private ExecutorService executorService;

    private ReceiveKafkaMessageUseCase<StationDataRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationArrivalDistanceInterModuleCommunicationService(
                loadAllStationArrivalDistanceRequestPort,
                createIfNotExistsStationArrivalDistanceRequestPort,
                loadStationPort,
                stationArrivalDistanceResponseSender,
                executorService
        );
    }


    @Test
    void testReceive_whenStationExists_shouldTriggerApplicationEvent() {
        StationDataRequest message = mock(StationDataRequest.class);
        String systemName = "system";
        String stationName = "station";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        Station station = mock(Station.class);
        when(station.name()).thenReturn(stationName);
        when(station.system()).thenReturn(system);
        Module requestingModule = mock(Module.class);
        when(message.systemName()).thenReturn(systemName);
        when(message.stationName()).thenReturn(stationName);
        when(message.requestingModule()).thenReturn(requestingModule);
        when(loadStationPort.load(systemName, stationName)).thenReturn(Optional.of(station));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.receive(message);

        verify(createIfNotExistsStationArrivalDistanceRequestPort).createIfNotExists(new StationArrivalDistanceRequest(systemName, stationName, requestingModule));
        verify(executorService).submit(runnableArgumentCaptor.capture());

        runnableArgumentCaptor.getValue().run();
        verify(loadStationPort).load(systemName, stationName);
        verify(stationArrivalDistanceResponseSender).sendResponsesForStation(systemName, stationName);
    }

    @Test
    void testReceive_whenStationExists_shouldNotTriggerApplicationEvent() {
        StationDataRequest message = mock(StationDataRequest.class);
        String systemName = "system";
        String stationName = "station";
        Module requestingModule = mock(Module.class);
        when(message.systemName()).thenReturn(systemName);
        when(message.stationName()).thenReturn(stationName);
        when(message.requestingModule()).thenReturn(requestingModule);
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        when(loadStationPort.load(systemName, stationName)).thenReturn(Optional.empty());

        underTest.receive(message);

        verify(createIfNotExistsStationArrivalDistanceRequestPort).createIfNotExists(new StationArrivalDistanceRequest(systemName, stationName, requestingModule));
        verify(executorService).submit(runnableArgumentCaptor.capture());

        // execute the runnable
        runnableArgumentCaptor.getValue().run();
        verify(loadStationPort).load(systemName, stationName);
        verify(stationArrivalDistanceResponseSender, never()).sendResponsesForStation(any(), any());
    }
}
