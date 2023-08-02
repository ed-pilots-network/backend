package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
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
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    @Mock
    private SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    @Mock
    private KafkaMessageMapper kafkaMessageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;

    private ProcessPendingDataRequestUseCase<SystemCoordinateRequest> underTest;

    @BeforeEach
    void setUp() {
        Executor executor = Runnable::run;
        underTest = new ProcessPendingSystemCoordinateRequestService(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, kafkaMessageMapper, objectMapper, retryTemplate, executor);
    }

    @Test
    void processPending_emptyRequestList() {
        // Given
        when(loadAllSystemCoordinateRequestPort.load()).thenReturn(Collections.emptyList());

        // When
        underTest.processPending();

        // Then
        verify(loadAllSystemCoordinateRequestPort, times(1)).load();
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort);
        verifyNoInteractions(loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemNotFound() {
        // Given
        SystemCoordinateRequest systemCoordinateRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinateRequest.systemName()).thenReturn("SystemName");
        List<SystemCoordinateRequest> systemCoordinateRequestList = List.of(systemCoordinateRequest);
        when(loadAllSystemCoordinateRequestPort.load()).thenReturn(systemCoordinateRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.empty());

        // When
        underTest.processPending();

        // Then
        verify(loadAllSystemCoordinateRequestPort, times(1)).load();
        verify(loadSystemPort, times(1)).load("SystemName");
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort, loadSystemPort);
        verifyNoInteractions(sendKafkaMessagePort, deleteSystemCoordinateRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemFound_sendFailed() {
        // Given
        SystemCoordinateRequest systemCoordinateRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinateRequest.systemName()).thenReturn("SystemName");
        when(systemCoordinateRequest.requestingModule()).thenReturn("trade");
        List<SystemCoordinateRequest> systemCoordinateRequestList = List.of(systemCoordinateRequest);
        System system = mock(System.class);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        KafkaMessage kafkaMessage = new KafkaMessage("trade_systemCoordinatesDataResponse", "JSON_STRING");
        KafkaMessageDto kafkaMessageDto = mock(KafkaMessageDto.class);
        when(kafkaMessageMapper.map(kafkaMessage)).thenReturn(kafkaMessageDto);
        when(loadAllSystemCoordinateRequestPort.load()).thenReturn(systemCoordinateRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));
        when(sendKafkaMessagePort.send(kafkaMessageDto)).thenReturn(false);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        // When
        underTest.processPending();

        // Then
        verify(loadAllSystemCoordinateRequestPort, times(1)).load();
        verify(loadSystemPort, times(1)).load("SystemName");
        verify(sendKafkaMessagePort, times(1)).send(kafkaMessageDto);
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort);
        verifyNoInteractions(deleteSystemCoordinateRequestPort);
    }

    @Test
    void processPending_nonEmptyRequestList_systemFound_sendSucceeded() {
        // Given
        SystemCoordinateRequest systemCoordinateRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinateRequest.systemName()).thenReturn("SystemName");
        when(systemCoordinateRequest.requestingModule()).thenReturn("trade");
        List<SystemCoordinateRequest> systemCoordinateRequestList = List.of(systemCoordinateRequest);
        System system = mock(System.class);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        KafkaMessage kafkaMessage = new KafkaMessage("trade_systemCoordinatesDataResponse", "JSON_STRING");
        KafkaMessageDto kafkaMessageDto = mock(KafkaMessageDto.class);
        when(kafkaMessageMapper.map(kafkaMessage)).thenReturn(kafkaMessageDto);
        when(loadAllSystemCoordinateRequestPort.load()).thenReturn(systemCoordinateRequestList);
        when(loadSystemPort.load("SystemName")).thenReturn(Optional.of(system));
        when(sendKafkaMessagePort.send(kafkaMessageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

        // When
        underTest.processPending();

        // Then
        verify(loadAllSystemCoordinateRequestPort, times(1)).load();
        verify(loadSystemPort, times(1)).load("SystemName");
        verify(sendKafkaMessagePort, times(1)).send(kafkaMessageDto);
        verify(deleteSystemCoordinateRequestPort, times(1)).delete("SystemName", "trade");
        verifyNoMoreInteractions(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort);
    }
}
