package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.util.Module;
import lombok.SneakyThrows;
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
class ProcessPendingSystemEliteIdRequestServiceTest {

    @Mock
    private LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort;
    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private SendMessagePort sendMessagePort;
    @Mock
    private DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    @Mock
    private SystemEliteIdResponseMapper systemEliteIdResponseMapper;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;

    private ProcessPendingDataRequestUseCase<SystemEliteIdRequest> underTest;

    @BeforeEach
    void setUp() {
        Executor executor = Runnable::run;
        underTest = new ProcessPendingSystemEliteIdRequestService(loadAllSystemEliteIdRequestPort, loadSystemPort, sendMessagePort, deleteSystemEliteIdRequestPort, systemEliteIdResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }

    @Test
    void processPending_emptyRequestList() {

        when(loadAllSystemEliteIdRequestPort.loadAll()).thenReturn(Collections.emptyList());


        underTest.processPending();


        verify(loadAllSystemEliteIdRequestPort, times(1)).loadAll();
        verifyNoMoreInteractions(loadAllSystemEliteIdRequestPort);
        verifyNoInteractions(loadSystemPort, sendMessagePort, deleteSystemEliteIdRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemNotFound() {

        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.systemName()).thenReturn("SystemName");
        List<SystemEliteIdRequest> systemEliteIdRequestList = List.of(systemEliteIdRequest);
        when(loadAllSystemEliteIdRequestPort.loadAll()).thenReturn(systemEliteIdRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.empty());


        underTest.processPending();


        verify(loadAllSystemEliteIdRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verifyNoMoreInteractions(loadAllSystemEliteIdRequestPort, loadSystemPort);
        verifyNoInteractions(sendMessagePort, deleteSystemEliteIdRequestPort);
    }

    @SneakyThrows
    @Test
    void processPending_nonEmptyRequestList_systemFound_sendFailed() {

        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.systemName()).thenReturn("SystemName");
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("trade");
        when(systemEliteIdRequest.requestingModule()).thenReturn(module);
        List<SystemEliteIdRequest> systemEliteIdRequestList = List.of(systemEliteIdRequest);
        System system = mock(System.class);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenReturn("JSON_STRING");
        Message message = new Message("trade_systemEliteIdResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(message)).thenReturn(messageDto);
        when(loadAllSystemEliteIdRequestPort.loadAll()).thenReturn(systemEliteIdRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));
        when(sendMessagePort.send(messageDto)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.processPending();


        verify(loadAllSystemEliteIdRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verify(sendMessagePort, times(1)).send(messageDto);
        verifyNoMoreInteractions(loadAllSystemEliteIdRequestPort, loadSystemPort, sendMessagePort);
        verifyNoInteractions(deleteSystemEliteIdRequestPort);
    }

    @SneakyThrows
    @Test
    void processPending_nonEmptyRequestList_systemFound_sendSucceeded() {

        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.systemName()).thenReturn("SystemName");
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("trade");
        when(systemEliteIdRequest.requestingModule()).thenReturn(module);
        List<SystemEliteIdRequest> systemEliteIdRequestList = List.of(systemEliteIdRequest);
        System system = mock(System.class);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenReturn("JSON_STRING");
        Message message = new Message("trade_systemEliteIdResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(message)).thenReturn(messageDto);
        when(loadAllSystemEliteIdRequestPort.loadAll()).thenReturn(systemEliteIdRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));
        when(sendMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.processPending();


        verify(loadAllSystemEliteIdRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verify(sendMessagePort, times(1)).send(messageDto);
        verify(deleteSystemEliteIdRequestPort, times(1)).delete("SystemName", module);
        verifyNoMoreInteractions(loadAllSystemEliteIdRequestPort, loadSystemPort, sendMessagePort, deleteSystemEliteIdRequestPort);
    }


    @Test
    void processPending_nonEmptyRequestList_systemFound_writeValueAsStringThrowsJsonProcessingException() throws JsonProcessingException {
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.systemName()).thenReturn("SystemName");
        List<SystemEliteIdRequest> systemEliteIdRequestList = List.of(systemEliteIdRequest);
        System system = mock(System.class);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenThrow(new JsonProcessingException("Test exception") {
        });
        when(loadAllSystemEliteIdRequestPort.loadAll()).thenReturn(systemEliteIdRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));

        // No exception as it is a NOOP in async
        underTest.processPending();

        verify(loadAllSystemEliteIdRequestPort, times(1)).loadAll();
        verify(loadSystemPort, times(1)).load("SystemName");
        verifyNoMoreInteractions(loadAllSystemEliteIdRequestPort, loadSystemPort, sendMessagePort, deleteSystemEliteIdRequestPort);
    }
}
