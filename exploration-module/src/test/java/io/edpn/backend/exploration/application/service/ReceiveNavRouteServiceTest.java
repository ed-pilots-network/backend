package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.util.IdGenerator;
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
import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiveNavRouteServiceTest {

    public static final String UUID = "902cef08-8409-4a7b-bbd1-c9c583b28454";
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private SaveOrUpdateSystemPort saveOrUpdateSystemPort;
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
    private MessageDtoMapper messageMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;

    private ReceiveKafkaMessageUseCase<NavRouteMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        when(idGenerator.generateId()).thenReturn(java.util.UUID.fromString(UUID));
        Executor executor = Runnable::run;
        underTest = new ReceiveNavRouteService(idGenerator, saveOrUpdateSystemPort, sendMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, loadSystemEliteIdRequestBySystemNamePort, deleteSystemEliteIdRequestPort, systemEliteIdResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }

    @SneakyThrows
    @Test
    void testReceiveAndSendResponses() {
        String systemName = "system";
        long systemEliteId = 1L;
        String starClass = "K";
        double xCoord = 1.0;
        double yCoord = 2.0;
        double zCoord = 3.0;
        Module module = mock(Module.class);
        when(module.getName()).thenReturn("module");
        System system = mock(System.class);
        when(system.getName()).thenReturn(systemName);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.systemAddress()).thenReturn(systemEliteId);
        when(item.starSystem()).thenReturn(systemName);
        when(item.starClass()).thenReturn(starClass);
        when(item.starPos()).thenReturn(new Double[]{xCoord, yCoord, zCoord});
        when(saveOrUpdateSystemPort.saveOrUpdate(argThat(argument ->
                argument.getId().equals(java.util.UUID.fromString(UUID)) &&
                        argument.getEliteId().equals(systemEliteId) &&
                        argument.getName().equals(systemName) &&
                        argument.getStarClass().equals(starClass) &&
                        argument.getCoordinate().getX().equals(xCoord) &&
                        argument.getCoordinate().getY().equals(yCoord) &&
                        argument.getCoordinate().getZ().equals(zCoord)
        ))).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        when(systemCoordinatesRequest.getRequestingModule()).thenReturn(module);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        when(systemEliteIdRequest.getRequestingModule()).thenReturn(module);
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


        verify(sendMessagePort).send(coordinateMessageDto);
        verify(sendMessagePort).send(eliteIdMessageDto);
        verify(deleteSystemCoordinateRequestPort).delete(systemName, module);
        verify(deleteSystemEliteIdRequestPort).delete(systemName, module);
    }

    @SneakyThrows
    @Test
    void testReceive_writeValueAsStringThrowsJsonProcessingException() {
        String systemName = "system";
        long systemEliteId = 1L;
        String starClass = "K";
        double xCoord = 1.0;
        double yCoord = 2.0;
        double zCoord = 3.0;
        System system = mock(System.class);
        when(system.getName()).thenReturn(systemName);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.systemAddress()).thenReturn(systemEliteId);
        when(item.starSystem()).thenReturn(systemName);
        when(item.starClass()).thenReturn(starClass);
        when(item.starPos()).thenReturn(new Double[]{xCoord, yCoord, zCoord});
        when(saveOrUpdateSystemPort.saveOrUpdate(argThat(argument ->
                argument.getId().equals(java.util.UUID.fromString(UUID)) &&
                        argument.getEliteId().equals(systemEliteId) &&
                        argument.getName().equals(systemName) &&
                        argument.getStarClass().equals(starClass) &&
                        argument.getCoordinate().getX().equals(xCoord) &&
                        argument.getCoordinate().getY().equals(yCoord) &&
                        argument.getCoordinate().getZ().equals(zCoord)
        ))).thenReturn(system);
        SystemCoordinateRequest systemCoordinatesRequest = mock(SystemCoordinateRequest.class);
        List<SystemCoordinateRequest> systemCoordinatesRequestlist = List.of(systemCoordinatesRequest);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemCoordinatesRequestlist);
        SystemEliteIdRequest systemEliteIdRequest = mock(SystemEliteIdRequest.class);
        List<SystemEliteIdRequest> systemEliteIdRequestlist = List.of(systemEliteIdRequest);
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(systemEliteIdRequestlist);
        SystemCoordinatesResponse systemCoordinatesResponse = mock(SystemCoordinatesResponse.class);
        when(systemCoordinatesResponseMapper.map(system)).thenReturn(systemCoordinatesResponse);
        SystemEliteIdResponse systemEliteIdResponse = mock(SystemEliteIdResponse.class);
        when(systemEliteIdResponseMapper.map(system)).thenReturn(systemEliteIdResponse);
        when(objectMapper.writeValueAsString(systemCoordinatesResponse)).thenThrow(new JsonProcessingException("Test exception") {
        });
        when(objectMapper.writeValueAsString(systemEliteIdResponse)).thenThrow(new JsonProcessingException("Test exception") {
        });

        // No exception as it is a NOOP in async
        underTest.receive(message);

        verifyNoMoreInteractions(sendMessagePort, deleteSystemCoordinateRequestPort, deleteSystemEliteIdRequestPort);
    }

    @Test
    void testReceiveNoPendingRequests() {
        String systemName = "system";
        long systemEliteId = 1L;
        String starClass = "K";
        double xCoord = 1.0;
        double yCoord = 2.0;
        double zCoord = 3.0;
        System system = mock(System.class);
        when(system.getName()).thenReturn(systemName);
        NavRouteMessage.V1.Item item = mock(NavRouteMessage.V1.Item.class);
        NavRouteMessage.V1 message = mock(NavRouteMessage.V1.class);
        NavRouteMessage.V1.Payload payload = mock(NavRouteMessage.V1.Payload.class);
        when(message.message()).thenReturn(payload);
        when(payload.items()).thenReturn(new NavRouteMessage.V1.Item[]{item});
        when(item.systemAddress()).thenReturn(systemEliteId);
        when(item.starSystem()).thenReturn(systemName);
        when(item.starClass()).thenReturn(starClass);
        when(item.starPos()).thenReturn(new Double[]{xCoord, yCoord, zCoord});
        when(saveOrUpdateSystemPort.saveOrUpdate(argThat(argument ->
                argument.getId().equals(java.util.UUID.fromString(UUID)) &&
                        argument.getEliteId().equals(systemEliteId) &&
                        argument.getName().equals(systemName) &&
                        argument.getStarClass().equals(starClass) &&
                        argument.getCoordinate().getX().equals(xCoord) &&
                        argument.getCoordinate().getY().equals(yCoord) &&
                        argument.getCoordinate().getZ().equals(zCoord)
        ))).thenReturn(system);
        when(loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName)).thenReturn(Collections.emptyList());
        when(loadSystemEliteIdRequestBySystemNamePort.loadByName(systemName)).thenReturn(Collections.emptyList());


        underTest.receive(message);

        verifyNoInteractions(sendMessagePort);
        verifyNoInteractions(deleteSystemCoordinateRequestPort);
        verifyNoInteractions(deleteSystemEliteIdRequestPort);
    }
}
