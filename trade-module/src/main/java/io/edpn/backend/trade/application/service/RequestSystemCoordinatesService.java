package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestSystemCoordinatesService implements RequestDataUseCase<System> {

    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort;
    private final CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.getXCoordinate()) || Objects.isNull(system.getYCoordinate()) || Objects.isNull(system.getZCoordinate());
    }

    @Override
    public void request(System system) {
        final String systemName = system.getName();
        boolean shouldRequest = !existsSystemCoordinateRequestPort.exists(systemName);
        if (shouldRequest) {
            SystemDataRequest stationDataRequest = new SystemDataRequest();
            stationDataRequest.setRequestingModule("trade");
            stationDataRequest.setSystemName(systemName);

            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

            Message message = Message.builder()
                    .topic("systemCoordinatesRequest")
                    .message(jsonNode.toString())
                    .build();

            sendKafkaMessagePort.send(messageMapper.map(message));
            createSystemCoordinateRequestPort.create(systemName);
        }
    }
}
