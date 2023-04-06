package io.eddb.eddb2backend.application.dto.station;

import io.eddb.eddb2backend.domain.model.Station;
import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class GetStationResponse {

    Long id;
    String name;

    public static class Mapper {
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
}
