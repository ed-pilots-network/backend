package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateUpdatedEvent;
import io.edpn.backend.exploration.application.domain.SystemEliteIdUpdatedEvent;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.util.IdGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiveNavRouteServiceTest {

    public static final String UUID = "902cef08-8409-4a7b-bbd1-c9c583b28454";
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private SaveOrUpdateSystemPort saveOrUpdateSystemPort;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private ExecutorService executorService;

    private ReceiveKafkaMessageUseCase<NavRouteMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        when(idGenerator.generateId()).thenReturn(java.util.UUID.fromString(UUID));
        underTest = new ReceiveNavRouteService(idGenerator, saveOrUpdateSystemPort, applicationEventPublisher, executorService);
    }

    @SneakyThrows
    @Test
    void testReceiveAndSendResponses() {
        String systemName = "system";
        long systemEliteId = 1L;
        String primaryStarClass = "K";
        double xCoord = 1.0;
        double yCoord = 2.0;
        double zCoord = 3.0;
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.systemAddress()).thenReturn(systemEliteId);
        when(item.starSystem()).thenReturn(systemName);
        when(item.starClass()).thenReturn(primaryStarClass);
        when(item.starPos()).thenReturn(new Double[]{xCoord, yCoord, zCoord});
        when(saveOrUpdateSystemPort.saveOrUpdate(argThat(argument ->
                argument.id().equals(java.util.UUID.fromString(UUID)) &&
                        argument.eliteId().equals(systemEliteId) &&
                        argument.name().equals(systemName) &&
                        argument.primaryStarClass().equals(primaryStarClass) &&
                        argument.coordinate().x().equals(xCoord) &&
                        argument.coordinate().y().equals(yCoord) &&
                        argument.coordinate().z().equals(zCoord)
        ))).thenReturn(system);
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.receive(message);

        verify(executorService).submit(runnableArgumentCaptor.capture());

        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);

        verify(applicationEventPublisher).publishEvent(argThat(argument -> argument instanceof SystemCoordinateUpdatedEvent systemCoordinateUpdatedEvent && systemCoordinateUpdatedEvent.getSource().equals(underTest) && systemCoordinateUpdatedEvent.getSystemName().equals(systemName)));
        verify(applicationEventPublisher).publishEvent(argThat(argument -> argument instanceof SystemEliteIdUpdatedEvent systemEliteIdUpdatedEvent && systemEliteIdUpdatedEvent.getSource().equals(underTest) && systemEliteIdUpdatedEvent.getSystemName().equals(systemName)));
    }
}
