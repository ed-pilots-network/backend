package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.SystemCoordinatesResponse;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.util.Module;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemCoordinatesResponseSenderTest {

    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private LoadSystemCoordinateRequestByIdentifierPort loadSystemCoordinateRequestBySystemNamePort;
    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    @Mock
    private SendMessagePort sendMessagePort;
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
                objectMapper,
                retryTemplate,
                executorService);
    }

    @SneakyThrows
    @Test
    void onEvent_shouldProcessPendingRequest() {
        try (MockedStatic<SystemCoordinatesResponse> systemCoordinatesResponseMockedStatic = mockStatic(SystemCoordinatesResponse.class)) {
            String systemName = "systemName";
            Module module = mock(Module.class);
            when(module.getName()).thenReturn("module");
            SystemCoordinateRequest request1 = mock(SystemCoordinateRequest.class);
            when(request1.requestingModule()).thenReturn(module);
            when(loadSystemCoordinateRequestBySystemNamePort.loadByIdentifier(systemName)).thenReturn(List.of(request1));
            ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
            System mockSystem = mock(System.class);
            when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
            SystemCoordinatesResponse mockSystemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
            systemCoordinatesResponseMockedStatic.when(() -> SystemCoordinatesResponse.from(mockSystem)).thenReturn(mockSystemCoordinatesResponse);
            when(objectMapper.writeValueAsString(mockSystemCoordinatesResponse)).thenReturn("JSON_STRING");
            Message message = new Message("module_systemCoordinatesResponse", "JSON_STRING");
            when(sendMessagePort.send(message)).thenReturn(true);
            doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

            underTest.sendResponsesForSystem(systemName);

            verify(executorService).submit(runnableArgumentCaptor.capture());

            // Verify runnable
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
            verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
        }
    }

    @SneakyThrows
    @Test
    void onEvent_shouldDoNothingWhenSystemNotFound() {
        String systemName = "systemName";
        SystemCoordinateRequest request = mock(SystemCoordinateRequest.class);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByIdentifier(systemName)).thenReturn(List.of(request));
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.sendResponsesForSystem(systemName);
        verify(executorService).submit(runnableArgumentCaptor.capture());
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);

        verify(sendMessagePort, never()).send(any());
        verify(deleteSystemCoordinateRequestPort, never()).delete(any(), any());
    }

    @SneakyThrows
    @Test
    void onEvent_shouldNotDeleteRequestWhenSendFails() {
        try (MockedStatic<SystemCoordinatesResponse> systemCoordinatesResponseMockedStatic = mockStatic(SystemCoordinatesResponse.class)) {
            String systemName = "systemName";
            SystemCoordinateRequest request1 = mock(SystemCoordinateRequest.class);
            Module module = mock(Module.class);
            when(module.getName()).thenReturn("module");
            when(request1.requestingModule()).thenReturn(module);
            when(loadSystemCoordinateRequestBySystemNamePort.loadByIdentifier(systemName)).thenReturn(List.of(request1));
            ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
            System mockSystem = mock(System.class);
            when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
            SystemCoordinatesResponse mockSystemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
            systemCoordinatesResponseMockedStatic.when(() -> SystemCoordinatesResponse.from(mockSystem)).thenReturn(mockSystemCoordinatesResponse);
            when(objectMapper.writeValueAsString(mockSystemCoordinatesResponse)).thenReturn("JSON_STRING");
            Message message = new Message("module_systemCoordinatesResponse", "JSON_STRING");
            when(sendMessagePort.send(message)).thenReturn(false);
            doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

            underTest.sendResponsesForSystem(systemName);

            verify(executorService).submit(runnableArgumentCaptor.capture());

            // Verify runnable
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
            verify(sendMessagePort).send(message);
            verify(deleteSystemCoordinateRequestPort, never()).delete(systemName, module);
        }
    }
}
