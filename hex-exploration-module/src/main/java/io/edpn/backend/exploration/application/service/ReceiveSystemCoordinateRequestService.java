package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinateRequestService implements ReceiveKafkaMessageUseCase<SystemDataRequest> {

    private final static String TOPIC = "_systemCoordinatesDataResponse";

    private final CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void receive(SystemDataRequest message) {
        String systemName = message.getSystemName();
        String requestingModule = message.getRequestingModule();

        loadSystemPort.load(systemName).ifPresentOrElse(
                system -> {
                    SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                    String stringJson = objectMapper.valueToTree(systemCoordinatesResponse).toString();
                    KafkaMessage kafkaMessage = new KafkaMessage(requestingModule + TOPIC, stringJson);

                    if (!sendKafkaMessagePort.send(kafkaMessage)) {
                        createSystemCoordinateRequestPort.create(new SystemCoordinateRequest(systemName, requestingModule));
                    }
                },
                () -> createSystemCoordinateRequestPort.create(new SystemCoordinateRequest(systemName, requestingModule)));
    }
}
