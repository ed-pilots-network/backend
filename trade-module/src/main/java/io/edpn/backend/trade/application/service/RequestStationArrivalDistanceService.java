package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestStationArrivalDistanceService implements RequestDataUseCase<Station> {

    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final ExistsStationArrivalDistanceRequestPort existsStationArrivalDistanceRequestPort;
    private final CreateStationArrivalDistanceRequestPort createStationArrivalDistanceRequestPort;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getArrivalDistance());
    }

    @Override
    public synchronized void request(Station station) {
        String stationName = station.getName();
        String systemName = station.getSystem().getName();
        boolean shouldRequest = !existsStationArrivalDistanceRequestPort.exists(systemName, stationName);
        if (shouldRequest) {
            StationDataRequest stationDataRequest = new StationDataRequest(
                    Module.TRADE, station.getName(), station.getSystem().getName()
            );

            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

            Message message = Message.builder()
                    .topic(Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName())
                    .message(jsonNode.toString())
                    .build();

            sendKafkaMessagePort.send(messageMapper.map(message));
            createStationArrivalDistanceRequestPort.create(systemName, stationName);
        }
    }
}
