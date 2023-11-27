package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
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
class SystemCoordinatesResponseSenderTest {

    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    @Mock
    private SendMessagePort sendMessagePort;
    @Mock
    private SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    @Mock
    private MessageDtoMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private ExecutorService executorService;

    private SystemCoordinatesResponseSender underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinatesResponseSenderService(
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

    @SneakyThrows
    @Test
    void onEvent_shouldProcessPendingRequest() {
        String systemName = "systemName";
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
        SystemCoordinateRequest request1 = mock(SystemCoordinateRequest.class);
        when(request1.requestingModule()).thenReturn(module);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(List.of(request1));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        System mockSystem = mock(System.class);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
        SystemCoordinatesResponse mockSystemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(mockSystem)).thenReturn(mockSystemCoordinatesResponse);
        when(objectMapper.writeValueAsString(mockSystemCoordinatesResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemCoordinatesResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.sendResponsesForSystem(systemName);

        verify(executorService).submit(runnableArgumentCaptor.capture());

        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
    }

    @SneakyThrows
    @Test
    void onEvent_shouldThrowErrorWhenSystemNotFound() {
        String systemName = "systemName";
        SystemCoordinateRequest request = mock(SystemCoordinateRequest.class);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(List.of(request));
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            underTest.sendResponsesForSystem(systemName);
            verify(executorService).submit(runnableArgumentCaptor.capture());
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        });

        assertThat(exception.getMessage(), is("System with name systemName not found when application event for it was triggered"));
    }

    @SneakyThrows
    @Test
    void onEvent_shouldNotDeleteRequestWhenSendFails() {
        String systemName = "systemName";
        SystemCoordinateRequest request1 = mock(SystemCoordinateRequest.class);
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
        when(request1.requestingModule()).thenReturn(module);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(List.of(request1));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        System mockSystem = mock(System.class);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
        SystemCoordinatesResponse mockSystemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(mockSystem)).thenReturn(mockSystemCoordinatesResponse);
        when(objectMapper.writeValueAsString(mockSystemCoordinatesResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemCoordinatesResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.sendResponsesForSystem(systemName);

        verify(executorService).submit(runnableArgumentCaptor.capture());

        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(deleteSystemCoordinateRequestPort, never()).delete(systemName, module);
    }
}