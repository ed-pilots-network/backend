package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemEliteIdRequestService implements ReceiveKafkaMessageUseCase<SystemDataRequest> {


    private final CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort;
    private final LoadSystemEliteIdRequestPort loadSystemEliteIdRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendMessagePort sendMessagePort;
    private final SystemEliteIdResponseMapper systemEliteIdResponseMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;

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
        if (loadSystemEliteIdRequestPort.load(systemEliteIdRequest).isEmpty()) {
            createSystemEliteIdRequestPort.create(systemEliteIdRequest);
        }
    }
}
