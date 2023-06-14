package io.edpn.backend.commodityfinder.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.edpn.backend.commodityfinder.domain.model.RequestDataMessage;
import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.commodityfinder.domain.service.RequestDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestStationArrivalDistanceService implements RequestDataService<Station> {

    private final RequestDataMessageRepository requestDataMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getArrivalDistance());
    }

    @Override
    public void request(Station station) {
        JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();

        ObjectNode jsonNode = nodeFactory.objectNode();
        jsonNode.put("station", station.getName());
        jsonNode.put("system", station.getSystem().getName());

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic("stationArrivalDistanceDataRequest")
                .message(jsonNode)
                .build();

        requestDataMessageRepository.sendToKafka(requestDataMessage);
    }
}
