package io.edpn.backend.exploration.adapter.applicationevent;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesUpdatedEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class SystemCoordinateUpdatedEventListenerTest {

    private LoadSystemPort loadSystemPort;
    private LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private SendMessagePort sendMessagePort;
    private SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private MessageDtoMapper messageMapper;
    private ObjectMapper objectMapper;
    private RetryTemplate retryTemplate;
    private ExecutorService executorService;

    private SystemCoordinatesUpdatedEventListener underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinatesEventListener(
                loadSystemPort,
                loadSystemCoordinateRequestBySystemNamePort,
                deleteSystemCoordinateRequestPort,
                sendMessagePort,
                systemCoordinatesResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executorService);
    }

    @Test
    void onEvent() {
        fail();
    }
}