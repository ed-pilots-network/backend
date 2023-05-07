package io.edpn.backend.eddnrest.application.mapper;

import io.edpn.backend.eddnrest.application.dto.station.GetStationResponse;
import io.edpn.backend.eddnrest.domain.model.station.Station;

public class StationMapper {
    public static GetStationResponse map(Station station) {
        return GetStationResponse.builder()
                .id(station.id())
                .name(station.name())
                .build();
    }

    public static Station map(GetStationResponse getStationResponse) {
        return Station.builder()
                .id(getStationResponse.getId())
                .name(getStationResponse.getName())
                .build();
    }
}
