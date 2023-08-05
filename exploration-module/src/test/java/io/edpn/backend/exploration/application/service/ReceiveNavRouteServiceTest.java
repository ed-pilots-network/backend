package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiveNavRouteServiceTest {

    @Mock
    private CreateSystemPort createSystemPort;
    @Mock
    private LoadSystemPort loadSystemPort;
    @Mock
    private SaveSystemPort saveSystemPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
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

    private ReceiveKafkaMessageUseCase<NavRouteMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        Executor executor = Runnable::run;
        underTest = new ReceiveNavRouteService(createSystemPort, loadSystemPort, saveSystemPort, sendKafkaMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }

    @Test
    void testReceiveMessage_whenSystemExistsAndPropertiesAreSet_shouldSaveAndSendResponses() {
        
        String systemName = "system";
        String module = "module";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        when(system.eliteId()).thenReturn(1L);
        when(system.starClass()).thenReturn("K");
        Coordinate coordinate = mock(Coordinate.class);
        when(system.coordinate()).thenReturn(coordinate);
        when(coordinate.x()).thenReturn(1.0);
        when(coordinate.y()).thenReturn(2.0);
        when(coordinate.z()).thenReturn(3.0);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Message payload = mock(NavRouteMessage.V1.Message.class);
        when(message.getMessage()).thenReturn(payload);
        when(payload.getItems()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.getStarSystem()).thenReturn(systemName);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        Message kafkaMessage = new Message("module_systemCoordinatesDataResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(kafkaMessage)).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(saveSystemPort).save(system);
        verify(sendKafkaMessagePort).send(messageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
    }

    @Test
    void testReceiveMessage_whenSystemDoesNotExist_shouldCreateSaveAndSendResponses() {
        
        String systemName = "system";
        String module = "module";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        when(system.eliteId()).thenReturn(null);
        when(system.starClass()).thenReturn(null);
        when(system.coordinate()).thenReturn(null);
        when(system.withEliteId(1L)).thenReturn(system);
        when(system.withStarClass("K")).thenReturn(system);
        when(system.withCoordinate(any(Coordinate.class))).thenReturn(system);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Message payload = mock(NavRouteMessage.V1.Message.class);
        when(message.getMessage()).thenReturn(payload);
        when(payload.getItems()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.getStarSystem()).thenReturn(systemName);
        when(item.getStarPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.getStarClass()).thenReturn("K");
        when(item.getSystemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        when(createSystemPort.create(systemName)).thenReturn(system);
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        Message kafkaMessage = new Message("module_systemCoordinatesDataResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(kafkaMessage)).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(createSystemPort).create(systemName);
        verify(saveSystemPort).save(system);
        verify(sendKafkaMessagePort).send(messageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
    }

    @Test
    void testReceiveMessage_whenSystemExistsAndPropertiesAreNotSet_shouldUpdateSaveAndSendResponses() {
        
        String systemName = "system";
        String module = "module";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        when(system.eliteId()).thenReturn(null);
        when(system.starClass()).thenReturn(null);
        when(system.withEliteId(1L)).thenReturn(system);
        when(system.withStarClass("K")).thenReturn(system);
        when(system.withCoordinate(any(Coordinate.class))).thenReturn(system);
        Coordinate coordinate = mock(Coordinate.class);
        when(system.coordinate()).thenReturn(coordinate);
        when(coordinate.x()).thenReturn(null);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Message payload = mock(NavRouteMessage.V1.Message.class);
        when(message.getMessage()).thenReturn(payload);
        when(payload.getItems()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.getStarSystem()).thenReturn(systemName);
        when(item.getStarPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.getStarClass()).thenReturn("K");
        when(item.getSystemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.toString()).thenReturn("JSON_STRING");
        when(objectMapper.valueToTree(systemCoordinatesResponse)).thenReturn(jsonNode);
        Message kafkaMessage = new Message("module_systemCoordinatesDataResponse", "JSON_STRING");
        MessageDto messageDto = mock(MessageDto.class);
        when(messageMapper.map(kafkaMessage)).thenReturn(messageDto);
        when(sendKafkaMessagePort.send(messageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(system).withEliteId(1L);
        verify(system).withStarClass("K");
        verify(system).withCoordinate(any(Coordinate.class));
        verify(loadSystemPort).load(systemName);
        verify(saveSystemPort).save(system);
        verify(sendKafkaMessagePort).send(messageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
    }

    @Test
    void testReceiveMessage_whenSystemDoesNotExist_shouldCreateSaveAndSendResponses_noPendingRequests() {
        
        String systemName = "system";
        System system = mock(System.class);
        when(system.name()).thenReturn(systemName);
        when(system.eliteId()).thenReturn(null);
        when(system.starClass()).thenReturn(null);
        when(system.withEliteId(1L)).thenReturn(system);
        when(system.withStarClass("K")).thenReturn(system);
        when(system.withCoordinate(any(Coordinate.class))).thenReturn(system);
        Coordinate coordinate = mock(Coordinate.class);
        when(system.coordinate()).thenReturn(coordinate);
        when(coordinate.x()).thenReturn(null);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Message payload = mock(NavRouteMessage.V1.Message.class);
        when(message.getMessage()).thenReturn(payload);
        when(payload.getItems()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.getStarSystem()).thenReturn(systemName);
        when(item.getStarPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.getStarClass()).thenReturn("K");
        when(item.getSystemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        when(createSystemPort.create(systemName)).thenReturn(system);
        when(saveSystemPort.save(system)).thenReturn(system);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(Collections.emptyList());


        underTest.receive(message);


        verify(system).withEliteId(1L);
        verify(system).withStarClass("K");
        verify(system).withCoordinate(any(Coordinate.class));
        verify(createSystemPort).create(systemName);
        verify(saveSystemPort).save(system);
        verifyNoInteractions(sendKafkaMessagePort);
        verifyNoInteractions(deleteSystemCoordinateRequestPort);
    }


}
