package io.edpn.backend.commodityfinder.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.edpn.backend.commodityfinder.domain.model.RequestDataMessage;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.commodityfinder.domain.service.RequestDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestSystemEliteIdService implements RequestDataService<System> {

    private final RequestDataMessageRepository requestDataMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.getEliteId());
    }

    @Override
    public void request(System system) {
        JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();

        ObjectNode jsonNode = nodeFactory.objectNode();
        jsonNode.put("system", system.getName());

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic("systemEliteIdDataRequest")
                .message(jsonNode)
                .build();

        requestDataMessageRepository.sendToKafka(requestDataMessage);
    }
}
