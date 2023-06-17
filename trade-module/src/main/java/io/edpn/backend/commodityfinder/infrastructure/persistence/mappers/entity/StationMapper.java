package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.commodityfinder.domain.model.LandingPadSize;
import io.edpn.backend.commodityfinder.domain.model.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StationMapper {

    private final SystemMapper systemMapper;
    private final MarketDatumMapper marketDatumMapper;

    public Station map(StationEntity stationEntity) {
        return Station.builder()
                .id(stationEntity.getId())
                .marketId(stationEntity.getMarketId())
                .name(stationEntity.getName())
                .arrivalDistance(stationEntity.getArrivalDistance())
                .system(systemMapper.map(stationEntity.getSystem()))
                .planetary(stationEntity.isPlanetary())
                .requireOdyssey(stationEntity.isRequireOdyssey())
                .fleetCarrier(stationEntity.isFleetCarrier())
                .maxLandingPadSize(LandingPadSize.valueOf(stationEntity.getMaxLandingPadSize()))
                .marketUpdatedAt(stationEntity.getMarketUpdatedAt())
                .marketData(marketDatumMapper.map(stationEntity.getMarketData()))
                .build();
    }

    public StationEntity map(Station station) {
        return StationEntity.builder()
                .id(station.getId())
                .marketId(station.getMarketId())
                .name(station.getName())
                .arrivalDistance(station.getArrivalDistance())
                .system(systemMapper.map(station.getSystem()))
                .planetary(station.isPlanetary())
                .requireOdyssey(station.isRequireOdyssey())
                .fleetCarrier(station.isFleetCarrier())
                .maxLandingPadSize(Optional.ofNullable(station.getMaxLandingPadSize()).map(LandingPadSize::name).orElse(LandingPadSize.UNKNOWN.name()))
                .marketUpdatedAt(station.getMarketUpdatedAt())
                .marketData(marketDatumMapper.mapToEntity(station.getMarketData()))
                .build();
    }
}
