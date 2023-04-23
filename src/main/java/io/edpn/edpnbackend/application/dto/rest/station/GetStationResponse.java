package io.edpn.edpnbackend.application.dto.rest.station;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class GetStationResponse {

    Long id;
    String name;
}
