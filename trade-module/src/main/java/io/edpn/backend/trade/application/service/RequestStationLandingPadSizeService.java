package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestStationLandingPadSizeService implements RequestDataService<Station> {

    private final RequestDataMessageRepository requestDataMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getMaxLandingPadSize()) || LandingPadSize.UNKNOWN == station.getMaxLandingPadSize();
    }

    @Override
    public void request(Station station) {
        StationDataRequest stationDataRequest = new StationDataRequest(
                null, station.getName(), station.getSystem().getName()
        );
        JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic("tradeModuleStationMaxLandingPadSizeDataRequest")
                .message(jsonNode)
                .build();

        requestDataMessageRepository.sendToKafka(requestDataMessage);
    }
}
