package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
    private SendMessagePort sendMessagePort;
    @Mock
    private LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    @Mock
    private SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    @Mock
    private LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort;
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

    private ReceiveKafkaMessageUseCase<NavRouteMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        Executor executor = Runnable::run;
        underTest = new ReceiveNavRouteService(createSystemPort, loadSystemPort, saveSystemPort, sendMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, loadSystemEliteIdRequestBySystemNamePort, deleteSystemEliteIdRequestPort, systemEliteIdResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }

    @SneakyThrows
    @Test
    void testReceiveMessage_whenSystemExistsAndPropertiesAreSet_shouldSaveAndSendResponses() {

        String systemName = "system";
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
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
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.starSystem()).thenReturn(systemName);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.requestingModule()).thenReturn(module);
        List<SystemEliteIdRequest> systemEliteIdRequestlist = List.of(systemEliteIdRequest);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemEliteIdRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemCoordinatesResponse)).thenReturn("JSON_STRING");
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemCoordinatesResponse", "JSON_STRING");
        Message eliteIdKafkaMessage = new Message("module_systemEliteIdResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        MessageDto eliteIdMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(messageMapper.map(eliteIdKafkaMessage)).thenReturn(eliteIdMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(true);
        when(sendMessagePort.send(eliteIdMessageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(saveSystemPort).save(system);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(sendMessagePort).send(eliteIdMessageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
        verify(deleteSystemEliteIdRequestPort).delete(systemName, module);
    }

    @SneakyThrows
    @Test
    void testReceiveMessage_whenSystemDoesNotExist_shouldCreateSaveAndSendResponses() {

        String systemName = "system";
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
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
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.starSystem()).thenReturn(systemName);
        when(item.starPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.starClass()).thenReturn("K");
        when(item.systemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        when(createSystemPort.create(systemName)).thenReturn(system);
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.requestingModule()).thenReturn(module);
        List<SystemEliteIdRequest> systemEliteIdRequestlist = List.of(systemEliteIdRequest);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemEliteIdRequestlist);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemCoordinatesResponse)).thenReturn("JSON_STRING");
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemCoordinatesResponse", "JSON_STRING");
        Message eliteIdKafkaMessage = new Message("module_systemEliteIdResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        MessageDto eliteIdMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(messageMapper.map(eliteIdKafkaMessage)).thenReturn(eliteIdMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(true);
        when(sendMessagePort.send(eliteIdMessageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(loadSystemPort).load(systemName);
        verify(createSystemPort).create(systemName);
        verify(saveSystemPort).save(system);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(sendMessagePort).send(eliteIdMessageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
        verify(deleteSystemEliteIdRequestPort).delete(systemName, module);
    }

    @SneakyThrows
    @Test
    void testReceiveMessage_whenSystemExistsAndPropertiesAreNotSet_shouldUpdateSaveAndSendResponses() {

        String systemName = "system";
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
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
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.starSystem()).thenReturn(systemName);
        when(item.starPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.starClass()).thenReturn("K");
        when(item.systemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.requestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.requestingModule()).thenReturn(module);
        List<SystemEliteIdRequest> systemEliteIdRequestlist = List.of(systemEliteIdRequest);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemEliteIdRequestlist);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemCoordinatesResponse)).thenReturn("JSON_STRING");
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenReturn("JSON_STRING");
        Message coordinateKafkaMessage = new Message("module_systemCoordinatesResponse", "JSON_STRING");
        Message eliteIdKafkaMessage = new Message("module_systemEliteIdResponse", "JSON_STRING");
        MessageDto coordinateMessageDto = mock(MessageDto.class);
        MessageDto eliteIdMessageDto = mock(MessageDto.class);
        when(messageMapper.map(coordinateKafkaMessage)).thenReturn(coordinateMessageDto);
        when(messageMapper.map(eliteIdKafkaMessage)).thenReturn(eliteIdMessageDto);
        when(sendMessagePort.send(coordinateMessageDto)).thenReturn(true);
        when(sendMessagePort.send(eliteIdMessageDto)).thenReturn(true);
        doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());


        underTest.receive(message);


        verify(system).withEliteId(1L);
        verify(system).withStarClass("K");
        verify(system).withCoordinate(any(Coordinate.class));
        verify(loadSystemPort).load(systemName);
        verify(saveSystemPort).save(system);
        verify(sendMessagePort).send(coordinateMessageDto);
        verify(sendMessagePort).send(eliteIdMessageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
        verify(deleteSystemEliteIdRequestPort).delete(systemName, module);
    }

    @SneakyThrows
    @Test
    void testReceiveMessage_whenSystemExistsAndPropertiesAreNotSet_writeValueAsStringThrowsJsonProcessingException() {
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
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.starSystem()).thenReturn(systemName);
        when(item.starPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.starClass()).thenReturn("K");
        when(item.systemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.of(system));
        when(saveSystemPort.save(system)).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        List<SystemEliteIdRequest> systemEliteIdRequestlist = List.of(systemEliteIdRequest);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemEliteIdRequestlist);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemCoordinatesResponse)).thenThrow(new JsonProcessingException("Test exception") {
        });
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenThrow(new JsonProcessingException("Test exception") {
        });

        // No exception as it is a NOOP in async
        underTest.receive(message);

        verify(system).withEliteId(1L);
        verify(system).withStarClass("K");
        verify(system).withCoordinate(any(Coordinate.class));
        verify(loadSystemPort).load(systemName);
        verify(saveSystemPort).save(system);
        verifyNoMoreInteractions(sendMessagePort, deleteSystemCoordinateRequestPort, deleteSystemEliteIdRequestPort);
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
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.starSystem()).thenReturn(systemName);
        when(item.starPos()).thenReturn(new Double[]{1.0, 2.0, 3.0});
        when(item.starClass()).thenReturn("K");
        when(item.systemAddress()).thenReturn(1L);
        when(loadSystemPort.load(systemName)).thenReturn(Optional.empty());
        when(createSystemPort.create(systemName)).thenReturn(system);
        when(saveSystemPort.save(system)).thenReturn(system);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(Collections.emptyList());
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(Collections.emptyList());


        underTest.receive(message);


        verify(system).withEliteId(1L);
        verify(system).withStarClass("K");
        verify(system).withCoordinate(any(Coordinate.class));
        verify(createSystemPort).create(systemName);
        verify(saveSystemPort).save(system);
        verifyNoInteractions(sendMessagePort);
        verifyNoInteractions(deleteSystemCoordinateRequestPort);
        verifyNoInteractions(deleteSystemEliteIdRequestPort);
    }
}
