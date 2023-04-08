package io.eddb.eddb2backend.application.mapper;

import io.eddb.eddb2backend.application.dto.station.GetStationResponse;
import io.eddb.eddb2backend.domain.model.Station;

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
