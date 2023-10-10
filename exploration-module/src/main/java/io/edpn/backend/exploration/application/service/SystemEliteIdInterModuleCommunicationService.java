package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
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
public class SystemEliteIdInterModuleCommunicationService implements ReceiveKafkaMessageUseCase<SystemDataRequest>, ProcessPendingDataRequestUseCase<SystemEliteIdRequest> {


    private final LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort;
    private final CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort;
    private final DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendMessagePort sendMessagePort;
    private final SystemEliteIdResponseMapper systemEliteIdResponseMapper;
    private final MessageDtoMapper messageMapper;
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
                        SystemEliteIdResponse systemEliteIdResponse = systemEliteIdResponseMapper.map(system);
                        String stringJson = objectMapper.writeValueAsString(systemEliteIdResponse);
                        String topic = Topic.Response.SYSTEM_ELITE_ID.getFormattedTopicName(requestingModule);
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
        SystemEliteIdRequest systemEliteIdRequest = new SystemEliteIdRequest(systemName, requestingModule);
        createIfNotExistsSystemEliteIdRequestPort.createIfNotExists(systemEliteIdRequest);
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void processPending() {
        loadAllSystemEliteIdRequestPort.loadAll().parallelStream()
                .forEach(systemEliteIdRequest -> CompletableFuture.runAsync(() -> loadSystemPort.load(systemEliteIdRequest.systemName())
                        .ifPresent(system -> {
                            try {
                                SystemEliteIdResponse systemEliteIdsResponse = systemEliteIdResponseMapper.map(system);
                                String stringJson = objectMapper.writeValueAsString(systemEliteIdsResponse);
                                String topic = Topic.Response.SYSTEM_ELITE_ID.getFormattedTopicName(systemEliteIdRequest.requestingModule());
                                Message message = new Message(topic, stringJson);
                                MessageDto messageDto = messageMapper.map(message);

                                boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                                if (sendSuccessful) {
                                    deleteSystemEliteIdRequestPort.delete(systemEliteIdRequest.systemName(), systemEliteIdRequest.requestingModule());
                                }
                            } catch (JsonProcessingException jpe) {
                                throw new RuntimeException(jpe);
                            }
                        }), executor));
    }
}
