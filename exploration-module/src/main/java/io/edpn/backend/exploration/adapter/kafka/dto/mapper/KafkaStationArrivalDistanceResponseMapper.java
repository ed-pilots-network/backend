package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationArrivalDistanceResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaStationArrivalDistanceResponseMapper implements StationArrivalDistanceResponseMapper {
    @Override
    public StationArrivalDistanceResponse map(Station station) {
        return new StationArrivalDistanceResponse(
                station.name(),
                station.system().name(),
                station.distanceFromStar());
    }
}
