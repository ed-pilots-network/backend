package io.eddb.eddb2backend.application.dto.station;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class GetStationResponse {

    Long id;
    String name;
}
