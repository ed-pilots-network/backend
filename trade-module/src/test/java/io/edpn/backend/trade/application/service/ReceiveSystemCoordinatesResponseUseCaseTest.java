package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemCoordinatesResponse;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemCoordinatesResponseUseCaseTest {

    @Mock
    private IdGenerator idGenerator;
    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;
    @Mock
    private LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort;
    @Mock
    private CreateOrLoadSystemPort createOrLoadSystemPort;
    @Mock
    private ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort;
    @Mock
    private CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort;
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

    private ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemCoordinateInterModuleCommunicationService(
                idGenerator,
                loadSystemsByFilterPort,
                loadAllSystemCoordinateRequestsPort,
                createOrLoadSystemPort,
                existsSystemCoordinateRequestPort,
                createIfNotExistsSystemCoordinateRequestPort,
                deleteSystemCoordinateRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper
        );
    }

    @Test
    public void shouldReceiveSystemCoordinatesResponse() {
        SystemCoordinatesResponse message =
                new SystemCoordinatesResponse("system", 1.0, 2.0, 3.0);
        Coordinate coordinate = new Coordinate(1.0, 2.0, 3.0);

        System system = mock(System.class);
        when(createOrLoadSystemPort.createOrLoad(argThat(argument -> argument.name().equals("system")))).thenReturn(system);
        when(system.withCoordinate(coordinate)).thenReturn(system);

        underTest.receive(message);
        verify(updateSystemPort, times(1)).update(system);
        verify(deleteSystemCoordinateRequestPort, times(1)).delete("system");
    }
}
