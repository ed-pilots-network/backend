package io.edpn.backend.commodityfinder.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.commodityfinder.domain.model.RequestDataMessage;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.commodityfinder.domain.service.RequestDataService;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestSystemCoordinatesService implements RequestDataService<System> {

    private final RequestDataMessageRepository requestDataMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.getXCoordinate()) || Objects.isNull(system.getYCoordinate()) || Objects.isNull(system.getZCoordinate());
    }

    @Override
    public void request(System system) {
        SystemDataRequest stationDataRequest = new SystemDataRequest();
        stationDataRequest.setSystemName(system.getName());

        JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic("tradeModuleSystemCoordinatesDataRequest")
                .message(jsonNode)
                .build();

        requestDataMessageRepository.sendToKafka(requestDataMessage);
    }
}
