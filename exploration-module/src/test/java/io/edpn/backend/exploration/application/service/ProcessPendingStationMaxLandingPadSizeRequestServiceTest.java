package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.CreateIfNotExistsStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadAllStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessPendingStationMaxLandingPadSizeRequestServiceTest {

    @Mock
    private LoadAllStationMaxLandingPadSizeRequestPort loadAllStationMaxLandingPadSizeRequestPort;
    @Mock
    private CreateIfNotExistsStationMaxLandingPadSizeRequestPort createIfNotExistsStationMaxLandingPadSizeRequestPort;
    @Mock
    private LoadStationPort loadStationPort;
    @Mock
    private StationMaxLandingPadSizeResponseSender systemEliteIdResponseSender;
    @Mock
    private ExecutorService executorService;

    private ProcessPendingDataRequestUseCase<StationMaxLandingPadSizeRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationMaxLandingPadSizeInterModuleCommunicationService(
                loadAllStationMaxLandingPadSizeRequestPort,
                createIfNotExistsStationMaxLandingPadSizeRequestPort,
                loadStationPort,
                systemEliteIdResponseSender,
                executorService
        );
    }

    @Test
    void testProcessPending_withMixedSystemAvailability() {
        // Setup
        StationMaxLandingPadSizeRequest existingSystemRequest = new StationMaxLandingPadSizeRequest("system", "existingStation", mock(Module.class));
        StationMaxLandingPadSizeRequest nonExistingSystemRequest = new StationMaxLandingPadSizeRequest("system", "nonExistingStation", mock(Module.class));
        when(loadAllStationMaxLandingPadSizeRequestPort.loadAll()).thenReturn(List.of(existingSystemRequest, nonExistingSystemRequest));

        System system = mock(System.class);
        when(system.name()).thenReturn("system");
        Station station = mock(Station.class);
        when(station.name()).thenReturn("existingStation");
        when(station.system()).thenReturn(system);
        when(loadStationPort.load("system", "existingStation")).thenReturn(Optional.of(station));
        when(loadStationPort.load("system", "nonExistingStation")).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.processPending();

        verify(executorService, times(2)).submit(runnableArgumentCaptor.capture());


        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(loadStationPort).load("system", "existingStation");
        verify(systemEliteIdResponseSender).sendResponsesForStation("system", "existingStation");
        verify(loadStationPort).load("system", "nonExistingStation");
        verify(systemEliteIdResponseSender, never()).sendResponsesForStation("system", "nonExistingStation");
    }
}
