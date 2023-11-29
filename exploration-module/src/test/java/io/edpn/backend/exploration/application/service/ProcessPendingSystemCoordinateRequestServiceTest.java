package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
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
class ProcessPendingSystemCoordinateRequestServiceTest {

    @Mock
    private LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort;
    @Mock
    private CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort;
    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private SystemCoordinatesResponseSender systemCoordinatesResponseSender;
    @Mock
    private ExecutorService executorService;
    private ProcessPendingDataRequestUseCase<SystemCoordinateRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateInterModuleCommunicationService(
                loadAllSystemCoordinateRequestPort,
                createIfNotExistsSystemCoordinateRequestPort,
                loadSystemPort,
                systemCoordinatesResponseSender,
                executorService
        );
    }

    @Test
    void testProcessPending_withMixedSystemAvailability() {
        // Setup
        SystemCoordinateRequest existingSystemRequest = new SystemCoordinateRequest("ExistingSystem", mock(Module.class));
        SystemCoordinateRequest nonExistingSystemRequest = new SystemCoordinateRequest("NonExistingSystem", mock(Module.class));
        when(loadAllSystemCoordinateRequestPort.loadAll()).thenReturn(List.of(existingSystemRequest, nonExistingSystemRequest));

        System existingSystem = mock(System.class);
        when(existingSystem.name()).thenReturn("ExistingSystem");
        when(loadSystemPort.load("ExistingSystem")).thenReturn(Optional.of(existingSystem));
        when(loadSystemPort.load("NonExistingSystem")).thenReturn(Optional.empty());

        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.processPending();

        verify(executorService, times(2)).submit(runnableArgumentCaptor.capture());


        // Verify first runnable call
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(loadSystemPort).load("ExistingSystem");
        verify(systemCoordinatesResponseSender).sendResponsesForSystem("ExistingSystem");

        // verify second runnable call
        verify(loadSystemPort).load("NonExistingSystem");
        verify(systemCoordinatesResponseSender, never()).sendResponsesForSystem("NonExistingSystem");
    }
}
