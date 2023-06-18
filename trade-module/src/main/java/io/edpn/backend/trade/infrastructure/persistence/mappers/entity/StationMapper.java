package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.Station;
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
                .planetary(stationEntity.getPlanetary())
                .requireOdyssey(stationEntity.getRequireOdyssey())
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
                .planetary(station.getPlanetary())
                .requireOdyssey(station.getRequireOdyssey())
                .fleetCarrier(station.isFleetCarrier())
                .maxLandingPadSize(Optional.ofNullable(station.getMaxLandingPadSize()).map(LandingPadSize::name).orElse(LandingPadSize.UNKNOWN.name()))
                .marketUpdatedAt(station.getMarketUpdatedAt())
                .marketData(marketDatumMapper.mapToEntity(station.getMarketData()))
                .build();
    }
}
