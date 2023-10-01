package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemCoordinatesResponseUseCaseTest {

    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;
    @Mock
    private LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort;
    @Mock
    private CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    @Mock
    private UpdateSystemPort updateSystemPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private Executor executor;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private MessageMapper messageMapper;

    private ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemCoordinateInterModuleCommunicationService(
                loadSystemsByFilterPort,
                loadAllSystemCoordinateRequestsPort,
                loadOrCreateSystemByNamePort,
                existsSystemCoordinateRequestPort,
                createSystemCoordinateRequestPort,
                deleteSystemCoordinateRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Test
    public void shouldReceiveSystemCoordinatesResponse() {
        SystemCoordinatesResponse message =
                new SystemCoordinatesResponse("system", 1.0, 2.0, 3.0);

        System system = System.builder()
                .name("system")
                .build();
        when(loadOrCreateSystemByNamePort.loadOrCreateSystemByName("system")).thenReturn(system);

        underTest.receive(message);

        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(anyString());
        verify(updateSystemPort, times(1)).update(any());

        assert(system.getXCoordinate() == 1.0);
        assert(system.getYCoordinate() == 2.0);
        assert(system.getZCoordinate() == 3.0);
    }
}
