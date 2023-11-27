package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.domain.SystemEliteIdUpdatedEvent;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessPendingSystemEliteIdRequestServiceTest {

    @Mock
    private LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort;
    @Mock
    private CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort;
    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private ExecutorService executorService;

    private ProcessPendingDataRequestUseCase<SystemEliteIdRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemEliteIdInterModuleCommunicationService(
                loadAllSystemEliteIdRequestPort,
                createIfNotExistsSystemEliteIdRequestPort,
                loadSystemPort,
                applicationEventPublisher,
                executorService
        );
    }

    @Test
    void testProcessPending_withMixedSystemAvailability() {
        // Setup
        SystemEliteIdRequest existingSystemRequest = new SystemEliteIdRequest("ExistingSystem", mock(Module.class));
        SystemEliteIdRequest nonExistingSystemRequest = new SystemEliteIdRequest("NonExistingSystem", mock(Module.class));
        when(loadAllSystemEliteIdRequestPort.loadAll()).thenReturn(List.of(existingSystemRequest, nonExistingSystemRequest));

        System existingSystem = mock(System.class);
        when(existingSystem.name()).thenReturn("ExistingSystem");
        when(loadSystemPort.load("ExistingSystem")).thenReturn(Optional.of(existingSystem));
        when(loadSystemPort.load("NonExistingSystem")).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.processPending();

        verify(executorService, times(2)).submit(runnableArgumentCaptor.capture());


        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(loadSystemPort).load("ExistingSystem");
        verify(applicationEventPublisher).publishEvent(argThat(argument -> argument instanceof SystemEliteIdUpdatedEvent systemEliteIdUpdatedEvent && systemEliteIdUpdatedEvent.getSource().equals(underTest) && systemEliteIdUpdatedEvent.getSystemName().equals("ExistingSystem")));
        verify(loadSystemPort).load("NonExistingSystem");
        verify(applicationEventPublisher, never()).publishEvent(argThat(argument -> argument instanceof SystemEliteIdUpdatedEvent systemEliteIdUpdatedEvent && systemEliteIdUpdatedEvent.getSource().equals(underTest) && systemEliteIdUpdatedEvent.getSystemName().equals("NonExistingSystem")));
    }
}
