package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestStationArrivalDistanceService implements RequestDataUseCase<Station> {

    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getArrivalDistance());
    }

    @Override
    public void request(Station station) {
        StationDataRequest stationDataRequest = new StationDataRequest();
        stationDataRequest.setStationName(station.getName());
        stationDataRequest.setSystemName(station.getSystem().getName());

        JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

        Message message = Message.builder()
                .topic("tradeModuleStationArrivalDistanceDataRequest")
                .message(jsonNode.toString())
                .build();

        sendKafkaMessagePort.send(messageMapper.map(message));
    }
}
