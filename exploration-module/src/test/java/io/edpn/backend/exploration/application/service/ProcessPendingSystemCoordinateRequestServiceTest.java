package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.mock;
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
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private ExecutorService executorService;
    private ProcessPendingDataRequestUseCase<SystemCoordinateRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateInterModuleCommunicationService(
                loadAllSystemCoordinateRequestPort,
                createIfNotExistsSystemCoordinateRequestPort,
                loadSystemPort,
                applicationEventPublisher,
                executorService
        );
    }

    @Test
    void testProcessPending_withMixedSystemAvailability() {
        // Setup
        SystemCoordinateRequest existingSystemRequest = new SystemCoordinateRequest("ExistingSystem", mock(Module.class));
        SystemCoordinateRequest nonExistingSystemRequest = new SystemCoordinateRequest("NonExistingSystem", mock(Module.class));
        when(loadAllSystemCoordinateRequestPort.loadAll()).thenReturn(List.of(existingSystemRequest, nonExistingSystemRequest));
        ArgumentCaptor<Runnable> taskCaptor = ArgumentCaptor.forClass(Runnable.class);

        System existingSystem = mock(System.class);
        when(loadSystemPort.load("ExistingSystem")).thenReturn(Optional.of(existingSystem));
        when(loadSystemPort.load("NonExistingSystem")).thenReturn(Optional.empty());

        // Execute
        underTest.processPending();

        // Verify
        verify(loadSystemPort).load("ExistingSystem");
        verify(loadSystemPort).load("NonExistingSystem");
        verify(executorService).submit(taskCaptor.capture());
        taskCaptor.getValue().run();
    }
}
