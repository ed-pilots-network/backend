package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemEliteIdResponseUseCaseTest {

    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;
    @Mock
    private LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private ExistsSystemEliteIdRequestPort existsSystemEliteIdRequestPort;
    @Mock
    private CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort;
    @Mock
    private DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
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

    private ReceiveKafkaMessageUseCase<SystemEliteIdResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemEliteIdInterModuleCommunicationService(
                loadSystemsByFilterPort,
                loadAllSystemEliteIdRequestsPort,
                loadOrCreateSystemByNamePort,
                existsSystemEliteIdRequestPort,
                createSystemEliteIdRequestPort,
                deleteSystemEliteIdRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Test
    public void shouldReceiveSystemEliteIdResponse() {
        SystemEliteIdResponse message = new SystemEliteIdResponse(
                "system", 1234
        );

        System system = System.builder()
                .name("system")
                .build();
        when(loadOrCreateSystemByNamePort.loadOrCreateSystemByName("system")).thenReturn(system);

        underTest.receive(message);

        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(anyString());
        verify(updateSystemPort, times(1)).update(any());
        verify(deleteSystemEliteIdRequestPort, times(1)).delete("system");

        assertEquals(1234, system.getEliteId());
    }
}
