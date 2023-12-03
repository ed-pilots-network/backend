package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationMaxLandingPadSizeResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class KafkaStationMaxLandingPadSizeResponseMapper implements StationMaxLandingPadSizeResponseMapper {

    @Override
    public StationMaxLandingPadSizeResponse map(Station station) {

        return new StationMaxLandingPadSizeResponse(
                station.name(),
                station.system().name(),
                getMaxLandingPadSize(station.landingPads()).name());
    }

    private LandingPadSize getMaxLandingPadSize(Map<LandingPadSize, Integer> landingPads) {
        if (Objects.isNull(landingPads) || landingPads.isEmpty()) {
            return LandingPadSize.UNKNOWN;
        }

        if (landingPads.containsKey(LandingPadSize.LARGE)) {
            return LandingPadSize.LARGE;
        }
        if (landingPads.containsKey(LandingPadSize.MEDIUM)) {
            return LandingPadSize.MEDIUM;
        }
        if (landingPads.containsKey(LandingPadSize.SMALL)) {
            return LandingPadSize.SMALL;
        }

        return LandingPadSize.UNKNOWN;
    }
}
