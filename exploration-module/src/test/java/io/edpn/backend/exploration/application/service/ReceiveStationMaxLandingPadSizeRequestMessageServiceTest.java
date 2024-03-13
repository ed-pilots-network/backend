package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.CreateIfNotExistsStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadAllStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.StationDataRequest;
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
public class ReceiveStationMaxLandingPadSizeRequestMessageServiceTest {

    @Mock
    private LoadAllStationMaxLandingPadSizeRequestPort loadAllStationMaxLandingPadSizeRequestPort;
    @Mock
    private CreateIfNotExistsStationMaxLandingPadSizeRequestPort createIfNotExistsStationMaxLandingPadSizeRequestPort;
    @Mock
    private LoadStationPort loadStationPort;
    @Mock
    private StationMaxLandingPadSizeResponseSender stationMaxLandingPadSizeResponseSender;
    @Mock
    private ExecutorService executorService;

    private ReceiveKafkaMessageUseCase<StationDataRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationMaxLandingPadSizeInterModuleCommunicationService(
                loadAllStationMaxLandingPadSizeRequestPort,
                createIfNotExistsStationMaxLandingPadSizeRequestPort,
                loadStationPort,
                stationMaxLandingPadSizeResponseSender,
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

        verify(createIfNotExistsStationMaxLandingPadSizeRequestPort).createIfNotExists(new StationMaxLandingPadSizeRequest(systemName, stationName, requestingModule));
        verify(executorService).submit(runnableArgumentCaptor.capture());

        runnableArgumentCaptor.getValue().run();
        verify(loadStationPort).load(systemName, stationName);
        verify(stationMaxLandingPadSizeResponseSender).sendResponsesForStation(systemName, stationName);
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

        verify(createIfNotExistsStationMaxLandingPadSizeRequestPort).createIfNotExists(new StationMaxLandingPadSizeRequest(systemName, stationName, requestingModule));
        verify(executorService).submit(runnableArgumentCaptor.capture());

        // execute the runnable
        runnableArgumentCaptor.getValue().run();
        verify(loadStationPort).load(systemName, stationName);
        verify(stationMaxLandingPadSizeResponseSender, never()).sendResponsesForStation(any(), any());
    }
}
