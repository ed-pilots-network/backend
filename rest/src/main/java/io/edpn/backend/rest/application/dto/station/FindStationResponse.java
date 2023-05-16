package io.edpn.backend.rest.application.dto.station;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class FindStationResponse {

    Long id;
    String name;
}
