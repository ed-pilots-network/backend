package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationMaxLandingPadSizeResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaStationMaxLandingPadSizeResponseMapper implements StationMaxLandingPadSizeResponseMapper {

    @Override
    public StationMaxLandingPadSizeResponse map(Station station) {

        return new StationMaxLandingPadSizeResponse(
                station.system().name(),
                station.name(),
                getMaxLandingPadSize(station.landingPads()).name());
    }

    private LandingPadSize getMaxLandingPadSize(Map<LandingPadSize, Integer> landingPads) {
        landingPads.containsKey(LandingPadSize.LARGE); // TODO: fix and fix for mapping to save to DB
        return LandingPadSize.UNKNOWN;
    }
}
