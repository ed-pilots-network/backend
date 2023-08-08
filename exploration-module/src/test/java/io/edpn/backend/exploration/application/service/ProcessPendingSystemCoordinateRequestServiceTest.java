package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
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
import java.util.Optional;
import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessPendingSystemCoordinateRequestServiceTest {

    @Mock
    private LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort;
    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private SendMessagePort sendMessagePort;
    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    @Mock
    private SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;

    private ProcessPendingDataRequestUseCase<SystemCoordinateRequest> underTest;

    @BeforeEach
    void setUp() {
        Executor executor = Runnable::run;
        underTest = new ProcessPendingSystemCoordinateRequestService(loadAllSystemCoordinateRequestPort, loadSystemPort, sendMessagePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }

    @Test
    void processPending_emptyRequestList() {

        when(loadAllSystemCoordinateRequestPort.loadAll()).thenReturn(Collections.emptyList());


        underTest.processPending();


        verify(loadAllSystemCoordinateRequestPort, times(1)).loadAll();
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort);
        verifyNoInteractions(loadSystemPort, sendMessagePort, deleteSystemCoordinateRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemNotFound() {

        SystemCoordinateRequest systemCoordinateRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinateRequest.systemName()).thenReturn("SystemName");
        List<SystemCoordinateRequest> systemCoordinateRequestList = List.of(systemCoordinateRequest);
        when(loadAllSystemCoordinateRequestPort.loadAll()).thenReturn(systemCoordinateRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.empty());


        underTest.processPending();


        verify(loadAllSystemCoordinateRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort, loadSystemPort);
        verifyNoInteractions(sendMessagePort, deleteSystemCoordinateRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemFound_sendFailed() {

        SystemCoordinateRequest systemCoordinateRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinateRequest.systemName()).thenReturn("SystemName");
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("trade");
        when(systemCoordinateRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinateRequestList = List.of(systemCoordinateRequest);
        System system = mock(System.class);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        Message message = new Message("trade_systemCoordinatesResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(message)).thenReturn(messageDto);
        when(loadAllSystemCoordinateRequestPort.loadAll()).thenReturn(systemCoordinateRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));
        when(sendMessagePort.send(messageDto)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.processPending();


        verify(loadAllSystemCoordinateRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verify(sendMessagePort, times(1)).send(messageDto);
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort, loadSystemPort, sendMessagePort);
        verifyNoInteractions(deleteSystemCoordinateRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemFound_sendSucceeded() {

        SystemCoordinateRequest systemCoordinateRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinateRequest.systemName()).thenReturn("SystemName");
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("trade");
        when(systemCoordinateRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinateRequestList = List.of(systemCoordinateRequest);
        System system = mock(System.class);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        Message message = new Message("trade_systemCoordinatesResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(message)).thenReturn(messageDto);
        when(loadAllSystemCoordinateRequestPort.loadAll()).thenReturn(systemCoordinateRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));
        when(sendMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.processPending();


        verify(loadAllSystemCoordinateRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verify(sendMessagePort, times(1)).send(messageDto);
        verify(deleteSystemCoordinateRequestPort, times(1)).delete("SystemName", module);
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort, loadSystemPort, sendMessagePort, deleteSystemCoordinateRequestPort);
    }
}
