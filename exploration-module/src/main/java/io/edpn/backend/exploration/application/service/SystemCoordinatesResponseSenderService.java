package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinatesResponseSenderService implements SystemCoordinatesResponseSender {

    private final LoadSystemPort loadSystemPort;
    private final LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SendMessagePort sendMessagePort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final MessageDtoMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final ExecutorService executorService;


    @Override
    public void sendResponsesForSystem(String systemName) {
        loadSystemCoordinateRequestBySystemNamePort.loadByName(systemName).parallelStream()
                .forEach(systemCoordinateRequest ->
                        executorService.submit(() -> {
                            try {
                                System system = loadSystemPort.load(systemName).orElseThrow(() -> new IllegalStateException("System with name %s not found when application event for it was triggered".formatted(systemName)));
                                SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                                String stringJson = objectMapper.writeValueAsString(systemCoordinatesResponse);
                                String topic = Topic.Response.SYSTEM_COORDINATES.getFormattedTopicName(systemCoordinateRequest.requestingModule());
                                Message message = new Message(topic, stringJson);
                                MessageDto messageDto = messageMapper.map(message);

                                boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                                if (sendSuccessful) {
                                    deleteSystemCoordinateRequestPort.delete(systemName, systemCoordinateRequest.requestingModule());
                                }
                            } catch (JsonProcessingException jpe) {
                                throw new RuntimeException(jpe);
                            }
                        })
                );
    }
}
