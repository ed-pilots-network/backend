package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.RequestMissingSystemCoordinatesUseCase;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.Module;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import io.edpn.backend.util.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestMissingSystemCoordinatesUseCaseTest {

    private final Executor executor = Runnable::run;
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
    private ObjectMapper objectMapper;
    private RequestMissingSystemCoordinatesUseCase underTest;

    @BeforeEach
    public void setUp() {
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

    @Test
    public void testFindSystemsFilter() {
        FindSystemFilter findSystemFilter = FindSystemFilter.builder()
                .hasCoordinates(false)
                .build();

        assertThat(SystemCoordinateInterModuleCommunicationService.FIND_SYSTEM_FILTER, equalTo(findSystemFilter));
    }

    @Test
    public void testRequestMissingForZeroResults() {
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        underTest.requestMissing();

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createSystemCoordinateRequestPort, never()).create(any());
    }

    @Test
    public void testRequestMissingForOneResult() {
        System system = mock(System.class);
        when(system.name()).thenReturn("Alpha");
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system));
        JsonNode jsonNode = mock(JsonNode.class);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof SystemDataRequest systemDataRequest) {
                return Module.TRADE == systemDataRequest.requestingModule() && systemDataRequest.systemName().equals("Alpha");
            } else {
                return false;
            }
        }))).thenReturn(jsonNode);
        when(jsonNode.toString()).thenReturn("jsonNodeString");
        Message message = new Message(Topic.Request.SYSTEM_COORDINATES.getTopicName(), "jsonNodeString");
        when(sendKafkaMessagePort.send(message)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.requestMissing();

        verify(sendKafkaMessagePort).send(any());
        verify(createSystemCoordinateRequestPort).create(any());
    }

    @Test
    public void testRequestMissingForMultipleResult() {
        System system1 = mock(System.class);
        when(system1.name()).thenReturn("Alpha");
        System system2 = mock(System.class);
        when(system2.name()).thenReturn("Bravo");
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1, system2));
        JsonNode jsonNode1 = mock(JsonNode.class);
        JsonNode jsonNode2 = mock(JsonNode.class);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof SystemDataRequest systemDataRequest) {
                return Module.TRADE == systemDataRequest.requestingModule() && systemDataRequest.systemName().equals("Alpha");
            } else {
                return false;
            }
        }))).thenReturn(jsonNode1);
        when(objectMapper.valueToTree(argThat(argument -> {
            if (argument instanceof SystemDataRequest systemDataRequest) {
                return Module.TRADE == systemDataRequest.requestingModule() && systemDataRequest.systemName().equals("Bravo");
            } else {
                return false;
            }
        }))).thenReturn(jsonNode2);
        when(jsonNode1.toString()).thenReturn("jsonNodeString1");
        when(jsonNode2.toString()).thenReturn("jsonNodeString2");
        Message message1 = new Message(Topic.Request.SYSTEM_COORDINATES.getTopicName(), "jsonNodeString1");
        Message message2 = new Message(Topic.Request.SYSTEM_COORDINATES.getTopicName(), "jsonNodeString2");
        when(sendKafkaMessagePort.send(message1)).thenReturn(true);
        when(sendKafkaMessagePort.send(message2)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.requestMissing();

        verify(sendKafkaMessagePort, times(2)).send(any());
        verify(createSystemCoordinateRequestPort, times(2)).create(any());
    }
}