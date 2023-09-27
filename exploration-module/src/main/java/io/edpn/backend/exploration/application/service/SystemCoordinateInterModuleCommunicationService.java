package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateInterModuleCommunicationService implements ReceiveKafkaMessageUseCase<SystemDataRequest>, ProcessPendingDataRequestUseCase<SystemCoordinateRequest> {


    private final LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort;
    private final CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendMessagePort sendMessagePort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final Executor executor;

    @Override
    public void receive(SystemDataRequest message) {
        String systemName = message.systemName();
        Module requestingModule = message.requestingModule();

        loadSystemPort.load(systemName).ifPresentOrElse(
                system -> {
                    try {
                        SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                        String stringJson = objectMapper.writeValueAsString(systemCoordinatesResponse);
                        String topic = Topic.Response.SYSTEM_COORDINATES.getFormattedTopicName(requestingModule);
                        Message kafkaMessage = new Message(topic, stringJson);
                        MessageDto messageDto = messageMapper.map(kafkaMessage);

                        boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                        if (!sendSuccessful) {
                            saveRequest(systemName, requestingModule);
                        }
                    } catch (JsonProcessingException jpe) {
                        throw new RuntimeException(jpe);
                    }
                },
                () -> saveRequest(systemName, requestingModule));
    }

    private void saveRequest(String systemName, Module requestingModule) {
        SystemCoordinateRequest systemCoordinateDataRequest = new SystemCoordinateRequest(systemName, requestingModule);
        createIfNotExistsSystemCoordinateRequestPort.createIfNotExists(systemCoordinateDataRequest);
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void processPending() {
        loadAllSystemCoordinateRequestPort.loadAll().parallelStream()
                .forEach(systemCoordinateRequest -> CompletableFuture.runAsync(() -> loadSystemPort.load(systemCoordinateRequest.systemName())
                        .ifPresent(system -> {
                            try {
                                SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                                String stringJson = objectMapper.writeValueAsString(systemCoordinatesResponse);
                                String topic = Topic.Response.SYSTEM_COORDINATES.getFormattedTopicName(systemCoordinateRequest.requestingModule());
                                Message message = new Message(topic, stringJson);
                                MessageDto messageDto = messageMapper.map(message);

                                boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                                if (sendSuccessful) {
                                    deleteSystemCoordinateRequestPort.delete(systemCoordinateRequest.systemName(), systemCoordinateRequest.requestingModule());
                                }
                            } catch (JsonProcessingException jpe) {
                                log.error("Error processing JSON", jpe);
                                throw new RuntimeException(jpe);
                            }
                        }), executor));
    }
}
