package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemCoordinateRequestServiceTest {

    @Mock
    private CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;

    private ReceiveKafkaMessageUseCase<SystemDataRequest> underTest;

    @BeforeEach
    public void setup() {
        underTest = new ReceiveSystemCoordinateRequestService(createSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, systemCoordinatesResponseMapper, messageMapper, objectMapper, retryTemplate);
    }

    @Test
    void testReceive_whenSystemExistsAndSendSuccessful_shouldNotSaveRequest() {
        
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        String requestingModule = "module";
        System system = mock(System.class);
        when(message.getSystemName()).thenReturn(systemName);
        when(message.getRequestingModule()).thenReturn(requestingModule);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        String jsonString = "{\"json\":\"response\"}";
        JsonNode jsonNode = mock(JsonNode.class);
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        when(jsonNode.toString()).thenReturn(jsonString);
        Message kafkaMessage = new Message("module_systemCoordinatesResponse", jsonString);
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(kafkaMessage)).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(sendKafkaMessagePort).send(messageDto);
        verify(createSystemCoordinateRequestPort, never()).create(any());
    }

    @Test
    void testReceive_whenSystemExistsAndSendFails_shouldSaveRequest() {
        
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        String requestingModule = "module";
        System system = mock(System.class);
        when(message.getSystemName()).thenReturn(systemName);
        when(message.getRequestingModule()).thenReturn(requestingModule);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        String jsonString = "{\"json\":\"response\"}";
        JsonNode jsonNode = mock(JsonNode.class);
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        when(jsonNode.toString()).thenReturn(jsonString);
        Message kafkaMessage = new Message("module_systemCoordinatesResponse", jsonString);
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(kafkaMessage)).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());
        SystemCoordinateRequest systemCoordinateDataRequest = new SystemCoordinateRequest(systemName, requestingModule);


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(sendKafkaMessagePort).send(messageDto);
        verify(createSystemCoordinateRequestPort).create(systemCoordinateDataRequest);
    }

    @Test
    void testReceive_whenSystemDoesNotExist_shouldSaveRequest() {
        
        SystemDataRequest message = mock(SystemDataRequest.class);
        String systemName = "system";
        String requestingModule = "module";
        when(message.getSystemName()).thenReturn(systemName);
        when(message.getRequestingModule()).thenReturn(requestingModule);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        SystemCoordinateRequest systemCoordinateDataRequest = new SystemCoordinateRequest(systemName, requestingModule);


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(sendKafkaMessagePort, never()).send(any());
        verify(createSystemCoordinateRequestPort).create(systemCoordinateDataRequest);
    }


}
