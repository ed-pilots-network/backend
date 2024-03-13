package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestSystemCoordinatesServiceTest {

    @Mock
    private ObjectMapper objectMapper;
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
    private RequestDataUseCase<System> underTest;

    public static Stream<Arguments> provideDoublesForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, null, null, true),
                Arguments.of(0.0, null, null, true),
                Arguments.of(null, 0.0, null, true),
                Arguments.of(null, null, 0.0, true),
                Arguments.of(0.0, 0.0, 0.0, false),
                Arguments.of(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateInterModuleCommunicationService(
                idGenerator,
                loadSystemsByFilterPort,
                loadAllSystemCoordinateRequestsPort,
                createOrLoadSystemPort,
                existsSystemCoordinateRequestPort,
                createSystemCoordinateRequestPort,
                deleteSystemCoordinateRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper
        );
    }

    @ParameterizedTest
    @MethodSource("provideDoublesForCheckApplicability")
    void shouldCheckApplicability(Double xCoordinate, Double yCoordinate, Double zCoordinate, boolean expected) {
        System systemWithCoordinates = new System(
                null,
                null,
                null,
                new Coordinate(xCoordinate, yCoordinate, zCoordinate)
        );

        assertThat(underTest.isApplicable(systemWithCoordinates), is(expected));
    }

    @Test
    public void testRequestWhenIdExists() {
        String systemName = "Test System";
        System system = new System(
                null,
                null,
                systemName,
                null
        );

        when(existsSystemCoordinateRequestPort.exists(systemName)).thenReturn(true);

        underTest.request(system);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createSystemCoordinateRequestPort, never()).create(anyString());
    }

    @Test
    public void testRequestWhenIdDoesNotExist() {
        String systemName = "Test System";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);

        JsonNode mockJsonNode = mock(JsonNode.class);
        String jsonString = "jsonString";

        when(existsSystemCoordinateRequestPort.exists(systemName)).thenReturn(false);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof SystemDataRequest systemDataRequest) {
                return Module.TRADE == systemDataRequest.requestingModule() && systemDataRequest.systemName().equals(systemName);
            } else {
                return false;
            }
        }))).thenReturn(mockJsonNode);
        when(mockJsonNode.toString()).thenReturn(jsonString);
        Message message = new Message(Topic.Request.SYSTEM_COORDINATES.getTopicName(), jsonString);

        underTest.request(system);

        verify(sendKafkaMessagePort).send(message);
        verify(createSystemCoordinateRequestPort).create(systemName);
    }
}
