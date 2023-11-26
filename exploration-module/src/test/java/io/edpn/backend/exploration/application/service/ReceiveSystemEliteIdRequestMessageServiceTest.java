package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.domain.SystemEliteIdUpdatedEvent;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemEliteIdRequestMessageServiceTest {

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

    private ReceiveKafkaMessageUseCase<SystemDataRequest> underTest;

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
    void testReceive_whenSystemExists_shouldTriggerApplicationEvent() {
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        Module requestingModule = mock(Module.class);
        when(message.systemName()).thenReturn(systemName);
        when(message.requestingModule()).thenReturn(requestingModule);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.receive(message);

        verify(createIfNotExistsSystemEliteIdRequestPort).createIfNotExists(new SystemEliteIdRequest(systemName, requestingModule));
        verify(executorService).submit(runnableArgumentCaptor.capture());

        runnableArgumentCaptor.getValue().run();
        verify(loadSystemPort).load(systemName);
        verify(applicationEventPublisher).publishEvent(argThat(argument -> argument instanceof SystemEliteIdUpdatedEvent systemEliteIdUpdatedEvent && systemEliteIdUpdatedEvent.getSource().equals(underTest) && systemEliteIdUpdatedEvent.getSystemName().equals(systemName)));
    }

    @Test
    void testReceive_whenSystemExists_shouldNotTriggerApplicationEvent() {
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        Module requestingModule = mock(Module.class);
        when(message.systemName()).thenReturn(systemName);
        when(message.requestingModule()).thenReturn(requestingModule);
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());

        underTest.receive(message);

        verify(createIfNotExistsSystemEliteIdRequestPort).createIfNotExists(new SystemEliteIdRequest(systemName, requestingModule));
        verify(executorService).submit(runnableArgumentCaptor.capture());

        // execute the runnable
        runnableArgumentCaptor.getValue().run();
        verify(loadSystemPort).load(systemName);
        verify(applicationEventPublisher, never()).publishEvent(any());
    }
}
