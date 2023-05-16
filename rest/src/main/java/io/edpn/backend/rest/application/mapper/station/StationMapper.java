package io.edpn.backend.rest.application.mapper.station;

import io.edpn.backend.rest.application.dto.station.FindStationResponse;
import io.edpn.backend.rest.domain.model.station.Station;

public class StationMapper {
    public static FindStationResponse map(Station station) {
        return FindStationResponse.builder()
                .id(station.id())
                .name(station.name())
                .build();
    }

    public static Station map(FindStationResponse findStationResponse) {
        return Station.builder()
                .id(findStationResponse.getId())
                .name(findStationResponse.getName())
                .build();
    }
}
