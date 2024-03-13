package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.SystemEliteIdResponse;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
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
class SystemEliteIdResponseSenderTest {

    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private LoadSystemEliteIdRequestByIdentifierPort loadSystemEliteIdRequestBySystemNamePort;
    @Mock
    private DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    @Mock
    private SendMessagePort sendMessagePort;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private ExecutorService executorService;

    private SystemEliteIdResponseSender underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdResponseSenderService(
                loadSystemPort,
                loadSystemEliteIdRequestBySystemNamePort,
                deleteSystemEliteIdRequestPort,
                sendMessagePort,
                objectMapper,
                retryTemplate,
                executorService);
    }

    @SneakyThrows
    @Test
    void onEvent_shouldProcessPendingRequest() {
        try (MockedStatic<SystemEliteIdResponse> systemEliteIdResponseMockedStatic = mockStatic(SystemEliteIdResponse.class)) {
            String systemName = "systemName";
            Module module = mock(Module.class);
            when(module.getName()).thenReturn("module");
            SystemEliteIdRequest request1 = mock(SystemEliteIdRequest.class);
            when(request1.requestingModule()).thenReturn(module);
            when(loadSystemEliteIdRequestBySystemNamePort.loadByIdentifier(systemName)).thenReturn(List.of(request1));
            ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
            System mockSystem = mock(System.class);
            when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
            SystemEliteIdResponse mockSystemEliteIdResponse = mock(SystemEliteIdResponse.class);
            systemEliteIdResponseMockedStatic.when(() -> SystemEliteIdResponse.from(mockSystem)).thenReturn(mockSystemEliteIdResponse);
            when(objectMapper.writeValueAsString(mockSystemEliteIdResponse)).thenReturn("JSON_STRING");
            Message message = new Message("module_systemEliteIdResponse", "JSON_STRING");
            when(sendMessagePort.send(message)).thenReturn(true);
            doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

            underTest.sendResponsesForSystem(systemName);

            verify(executorService).submit(runnableArgumentCaptor.capture());

            // Verify runnable
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
            verify(deleteSystemEliteIdRequestPort).delete(systemName, module);
        }
    }

    @SneakyThrows
    @Test
    void onEvent_shouldThrowErrorWhenSystemNotFound() {
        String systemName = "systemName";
        SystemEliteIdRequest request = mock(SystemEliteIdRequest.class);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByIdentifier(systemName)).thenReturn(List.of(request));
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);


        underTest.sendResponsesForSystem(systemName);
        verify(executorService).submit(runnableArgumentCaptor.capture());
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);

        verify(sendMessagePort, never()).send(any());
        verify(deleteSystemEliteIdRequestPort, never()).delete(any(), any());
    }

    @SneakyThrows
    @Test
    void onEvent_shouldNotDeleteRequestWhenSendFails() {
        String systemName = "systemName";
        SystemEliteIdRequest request1 = mock(SystemEliteIdRequest.class);
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
        when(request1.requestingModule()).thenReturn(module);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByIdentifier(systemName)).thenReturn(List.of(request1));
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        System mockSystem = mock(System.class);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(mockSystem));
        SystemEliteIdResponse mockSystemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(objectMapper.writeValueAsString(mockSystemEliteIdResponse)).thenReturn("JSON_STRING");
        Message message = new Message("module_systemEliteIdResponse", "JSON_STRING");
        when(sendMessagePort.send(message)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        underTest.sendResponsesForSystem(systemName);

        verify(executorService).submit(runnableArgumentCaptor.capture());

        // Verify runnable
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
        verify(deleteSystemEliteIdRequestPort, never()).delete(systemName, module);
    }
}
