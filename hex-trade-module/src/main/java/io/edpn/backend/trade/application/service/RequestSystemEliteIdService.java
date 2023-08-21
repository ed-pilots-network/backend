package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestSystemEliteIdService implements RequestDataUseCase<System> {
    
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.getEliteId());
    }

    @Override
    public void request(System system) {
        SystemDataRequest systemDataRequest = new SystemDataRequest();
        systemDataRequest.setRequestingModule("trade");
        systemDataRequest.setSystemName(system.getName());

        JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);
        
        Message message = Message.builder()
                .topic("systemEliteIdRequest")
                .message(jsonNode.toString())
                .build();
        
        sendKafkaMessagePort.send(messageMapper.map(message));
    }
}
