package io.edpn.edpnbackend.application.mapper;

import io.edpn.edpnbackend.application.dto.rest.station.GetStationResponse;
import io.edpn.edpnbackend.domain.model.station.Station;

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
