package io.edpn.backend.eddnrest.application.dto.station;

import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class GetStationRequest {

    private final String name;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
