package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinateRequestService implements ReceiveKafkaMessageUseCase<SystemDataRequest> {

    private final static String TOPIC = "_systemCoordinatesDataResponse";

    private final CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final KafkaMessageMapper kafkaMessageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;

    @Override
    public void receive(SystemDataRequest message) {
        String systemName = message.getSystemName();
        String requestingModule = message.getRequestingModule();

        loadSystemPort.load(systemName).ifPresentOrElse(
                system -> {
                    SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                    String stringJson = objectMapper.valueToTree(systemCoordinatesResponse).toString();
                    KafkaMessage kafkaMessage = new KafkaMessage(requestingModule + TOPIC, stringJson);
                    KafkaMessageDto kafkaMessageDto = kafkaMessageMapper.map(kafkaMessage);

                    boolean sendSuccessful = retryTemplate.execute(retryContext -> sendKafkaMessagePort.send(kafkaMessageDto));
                    if (!sendSuccessful) {
                        saveRequest(systemName, requestingModule);
                    }
                },
                () -> saveRequest(systemName, requestingModule));
    }

    private void saveRequest(String systemName, String requestingModule) {
        SystemCoordinateRequest systemCoordinateDataRequest = new SystemCoordinateRequest(systemName, requestingModule);
        createSystemCoordinateRequestPort.create(systemCoordinateDataRequest);
    }
}
