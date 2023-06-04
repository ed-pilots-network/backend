package io.edpn.backend.modulith.commodityfinder.application.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.StationEntity;
import io.edpn.backend.modulith.commodityfinder.domain.entity.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationMapper {

    private final SystemMapper systemMapper;
//    private final MarketDatumMapper marketDatumMapper;

    public Station map(StationEntity stationEntity) {
        return Station.builder()
                .id(stationEntity.getId())
                .marketId(stationEntity.getMarketId())
                .name(stationEntity.getName())
                .system(systemMapper.map(stationEntity.getSystem()))
                .planetary(stationEntity.isPlanetary())
                .requireOdyssey(stationEntity.isRequireOdyssey())
                .fleetCarrier(stationEntity.isFleetCarrier())
                .maxLandingPadSize(stationEntity.getMaxLandingPadSize())
                .marketUpdatedAt(stationEntity.getMarketUpdatedAt())
//                .commodityMarketData(marketDatumMapper.map(stationEntity.getCommodityMarketData()))
                .build();
    }

    public StationEntity map(Station station) {
        return StationEntity.builder()
                .id(station.getId())
                .marketId(station.getMarketId())
                .name(station.getName())
                .system(systemMapper.map(station.getSystem()))
                .planetary(station.isPlanetary())
                .requireOdyssey(station.isRequireOdyssey())
                .fleetCarrier(station.isFleetCarrier())
                .maxLandingPadSize(station.getMaxLandingPadSize())
                .marketUpdatedAt(station.getMarketUpdatedAt())
//                .commodityMarketData(marketDatumMapper.map(station, station.getCommodityMarketData()))
                .build();
    }
}
