package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.domain.SystemEliteIdUpdatedEvent;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.util.Module;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemEliteIdUpdateEventListenerTest {

    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort;
    @Mock
    private DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    @Mock
    private SendMessagePort sendMessagePort;
    @Mock
    private SystemEliteIdResponseMapper systemEliteIdResponseMapper;
    @Mock
    private MessageDtoMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private ExecutorService executorService;

    private SystemEliteIdEventListenerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdEventListenerService(
                loadSystemPort,
                loadSystemEliteIdRequestBySystemNamePort,
                deleteSystemEliteIdRequestPort,
                sendMessagePort,
                systemEliteIdResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executorService);
    }

    @SneakyThrows
    @Test
    void onEvent_shouldProcessPendingRequest() {
        String systemName = "systemName";
        SystemEliteIdUpdatedEvent systemEliteIdUpdatedEvent = new SystemEliteIdUpdatedEvent(mock(SystemEliteIdInterModuleCommunicationService.class), systemName);
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
        SystemEliteIdRequest request1 = mock(SystemEliteIdRequest.class);
        when(request1.requestingModule()).thenReturn(module);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemEliteIdUpdatedEvent.getSystemName())).thenReturn(List.of(request1));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        System mockSystem = mock(System.class);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
        SystemEliteIdResponse mockSystemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(mockSystem)).thenReturn(mockSystemEliteIdResponse);
        when(objectMapper.writeValueAsString(mockSystemEliteIdResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemEliteIdResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.onUpdatedEvent(systemEliteIdUpdatedEvent);

        verify(executorService).submit(runnableArgumentCaptor.capture());

        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(deleteSystemEliteIdRequestPort).delete(systemName, module);
    }

    @SneakyThrows
    @Test
    void onEvent_shouldThrowErrorWhenSystemNotFound() {
        String systemName = "systemName";
        SystemEliteIdRequest request = mock(SystemEliteIdRequest.class);
        SystemEliteIdUpdatedEvent event = new SystemEliteIdUpdatedEvent(mock(SystemEliteIdInterModuleCommunicationService.class), systemName);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(List.of(request));
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            underTest.onUpdatedEvent(event);
            verify(executorService).submit(runnableArgumentCaptor.capture());
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        });

        assertThat(exception.getMessage(), is("System with name systemName not found when application event for it was triggered"));
    }

    @SneakyThrows
    @Test
    void onEvent_shouldNotDeleteRequestWhenSendFails() {
        String systemName = "systemName";
        SystemEliteIdUpdatedEvent systemEliteIdUpdatedEvent = new SystemEliteIdUpdatedEvent(mock(SystemEliteIdInterModuleCommunicationService.class), systemName);
        SystemEliteIdRequest request1 = mock(SystemEliteIdRequest.class);
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
        when(request1.requestingModule()).thenReturn(module);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemEliteIdUpdatedEvent.getSystemName())).thenReturn(List.of(request1));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        System mockSystem = mock(System.class);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
        SystemEliteIdResponse mockSystemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(mockSystem)).thenReturn(mockSystemEliteIdResponse);
        when(objectMapper.writeValueAsString(mockSystemEliteIdResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemEliteIdResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.onUpdatedEvent(systemEliteIdUpdatedEvent);

        verify(executorService).submit(runnableArgumentCaptor.capture());

        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(deleteSystemEliteIdRequestPort, never()).delete(systemName, module);
    }
}