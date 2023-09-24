package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.web.object.MessageDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.RequestMissingSystemEliteIdUseCase;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

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
public class RequestMissingSystemEliteIdUseCaseTest {
    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;

    @Mock
    private CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort;

    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;

    @Mock
    private RetryTemplate retryTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private MessageMapper messageMapper;

    private RequestMissingSystemEliteIdUseCase undertest;

    private final Executor executor = Runnable::run;

    @BeforeEach
    public void setUp() {
        undertest = new RequestMissingSystemEliteIdService(loadSystemsByFilterPort, createSystemEliteIdRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }

    @Test
    public void testFindSystemsFilter() {
        FindSystemFilter findSystemFilter = FindSystemFilter.builder()
                .hasEliteId(false)
                .build();

        assertThat(RequestMissingSystemEliteIdService.FIND_SYSTEM_FILTER, equalTo(findSystemFilter));
    }

    @Test
    public void testRequestMissingForZeroResults() {
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        undertest.requestMissing();

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createSystemEliteIdRequestPort, never()).create(any());
    }

    @Test
    public void testRequestMissingForOneResult() {
        System system = mock(System.class);
        when(system.getName()).thenReturn("Alpha");
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
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("systemEliteIdRequest") && argument.getMessage().equals("jsonNodeString")))).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        undertest.requestMissing();

        verify(sendKafkaMessagePort).send(any());
        verify(createSystemEliteIdRequestPort).create(any());
    }

    @Test
    public void testRequestMissingForMultipleResult() {
        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Alpha");
        System system2 = mock(System.class);
        when(system2.getName()).thenReturn("Bravo");
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
        MessageDto messageDto1 = mock(MessageDto.class);
        MessageDto messageDto2 = mock(MessageDto.class);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("systemEliteIdRequest") && argument.getMessage().equals("jsonNodeString1")))).thenReturn(messageDto1);
        when(messageMapper.map(argThat(argument -> argument != null && argument.getTopic().equals("systemEliteIdRequest") && argument.getMessage().equals("jsonNodeString2")))).thenReturn(messageDto2);
        when(sendKafkaMessagePort.send(messageDto1)).thenReturn(true);
        when(sendKafkaMessagePort.send(messageDto2)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        undertest.requestMissing();

        verify(sendKafkaMessagePort, times(2)).send(any());
        verify(createSystemEliteIdRequestPort, times(2)).create(any());
    }

}
