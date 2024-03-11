package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestSystemEliteIdServiceTest {

    @Mock
    private IdGenerator idGenerator;
    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;
    @Mock
    private LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort;
    @Mock
    private CreateOrLoadSystemPort createOrLoadSystemPort;
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

    private RequestDataUseCase<System> underTest;

    public static Stream<Arguments> provideLongForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(0L, false),
                Arguments.of(Long.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdInterModuleCommunicationService(
                idGenerator,
                loadSystemsByFilterPort,
                loadAllSystemEliteIdRequestsPort,
                createOrLoadSystemPort,
                existsSystemEliteIdRequestPort,
                createSystemEliteIdRequestPort,
                deleteSystemEliteIdRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper
        );
    }

    @ParameterizedTest
    @MethodSource("provideLongForCheckApplicability")
    void shouldCheckApplicability(Long eliteId, boolean expected) {
        System systemWithEliteId = new System(
                null,
                eliteId,
                null,
                null
        );

        assertThat(underTest.isApplicable(systemWithEliteId), is(expected));
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

        when(existsSystemEliteIdRequestPort.exists(systemName)).thenReturn(true);

        underTest.request(system);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createSystemEliteIdRequestPort, never()).create(anyString());
    }

    @Test
    public void testRequestWhenIdDoesNotExist() {
        String systemName = "Test System";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);

        JsonNode mockJsonNode = mock(JsonNode.class);
        String mockJsonString = "jsonString";

        when(existsSystemEliteIdRequestPort.exists(systemName)).thenReturn(false);
        when(objectMapper.valueToTree(argThat(arg -> {
            if (arg instanceof SystemDataRequest systemDataRequest) {
                return systemName.equals(systemDataRequest.systemName()) && Module.TRADE.equals(systemDataRequest.requestingModule());
            } else {
                return false;
            }
        }))).thenReturn(mockJsonNode);
        when(mockJsonNode.toString()).thenReturn(mockJsonString);

        underTest.request(system);

        verify(sendKafkaMessagePort).send(eq(new Message(Topic.Request.SYSTEM_ELITE_ID.getTopicName(), mockJsonString)));
        verify(createSystemEliteIdRequestPort).create(systemName);
    }
}
