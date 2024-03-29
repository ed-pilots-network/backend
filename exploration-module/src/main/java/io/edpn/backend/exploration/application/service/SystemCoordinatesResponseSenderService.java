package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.SystemCoordinatesResponse;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinatesResponseSenderService implements SystemCoordinatesResponseSender {

    private final LoadSystemPort loadSystemPort;
    private final LoadSystemCoordinateRequestByIdentifierPort loadSystemCoordinateRequestBySystemNamePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SendMessagePort sendMessagePort;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final ExecutorService executorService;


    @Override
    public void sendResponsesForSystem(String systemName) {
        loadSystemCoordinateRequestBySystemNamePort.loadByIdentifier(systemName).parallelStream()
                .forEach(systemCoordinateRequest -> executorService.submit(ConcurrencyUtil.errorHandlingWrapper(() -> {
                                    try {
                                        System system = loadSystemPort.load(systemName).orElseThrow(() -> new IllegalStateException("System with name %s not found when application event for it was triggered".formatted(systemName)));
                                        SystemCoordinatesResponse systemCoordinatesResponse = SystemCoordinatesResponse.from(system);
                                        String stringJson = objectMapper.writeValueAsString(systemCoordinatesResponse);
                                        String topic = Topic.Response.SYSTEM_COORDINATES.getFormattedTopicName(systemCoordinateRequest.requestingModule());
                                        Message message = new Message(topic, stringJson);

                                        boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(message));
                                        if (sendSuccessful) {
                                            deleteSystemCoordinateRequestPort.delete(systemName, systemCoordinateRequest.requestingModule());
                                        }
                                    } catch (JsonProcessingException jpe) {
                                        throw new RuntimeException(jpe);
                                    }
                                },
                                exception -> log.error("Error while processing systemCoordinatesResponse for system: {}", systemName, exception))
                ));
    }
}
