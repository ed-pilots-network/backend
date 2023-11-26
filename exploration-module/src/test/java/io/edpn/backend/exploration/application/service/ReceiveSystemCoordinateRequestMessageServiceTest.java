package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.domain.SystemCoordinatesUpdatedEvent;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemCoordinateRequestMessageServiceTest {

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

    private ReceiveKafkaMessageUseCase<SystemDataRequest> underTest;

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
    void testReceive_whenSystemExists_shouldTriggerApplicationEvent() {
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        System system = mock(System.class);
        Module requestingModule = mock(Module.class);
        when(requestingModule.getName()).thenReturn("module");
        when(message.systemName()).thenReturn(systemName);
        when(message.requestingModule()).thenReturn(requestingModule);

        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));

        underTest.receive(message);

        verify(createIfNotExistsSystemCoordinateRequestPort).createIfNotExists(new SystemCoordinateRequest(systemName, requestingModule));
        verify(loadSystemPort).load(systemName);
        verify(applicationEventPublisher).publishEvent(new SystemCoordinatesUpdatedEvent(underTest, systemName));
    }

    @Test
    void testReceive_whenSystemExists_shouldNotTriggerApplicationEvent() {
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        Module requestingModule = mock(Module.class);
        when(requestingModule.getName()).thenReturn("module");
        when(message.systemName()).thenReturn(systemName);
        when(message.requestingModule()).thenReturn(requestingModule);

        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());

        underTest.receive(message);

        verify(createIfNotExistsSystemCoordinateRequestPort).createIfNotExists(new SystemCoordinateRequest(systemName, requestingModule));
        verify(loadSystemPort).load(systemName);
        verify(applicationEventPublisher, never()).publishEvent(any());
    }
}
