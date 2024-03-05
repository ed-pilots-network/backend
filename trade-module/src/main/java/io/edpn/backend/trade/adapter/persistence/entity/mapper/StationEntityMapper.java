package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StationEntityMapper {

    private final SystemEntityMapper systemEntityMapper;
    private final MarketDatumEntityMapper marketDatumEntityMapper;

    public Station map(StationEntity stationEntity) {
        return new Station(
                stationEntity.getId(),
                stationEntity.getMarketId(),
                stationEntity.getName(),
                stationEntity.getArrivalDistance(),
                systemEntityMapper.map(stationEntity.getSystem()),
                stationEntity.getPlanetary(),
                stationEntity.getRequireOdyssey(),
                stationEntity.getFleetCarrier(),
                LandingPadSize.valueOf(stationEntity.getMaxLandingPadSize()),
                stationEntity.getMarketUpdatedAt(),
                stationEntity.getMarketData().stream().map(marketDatumEntityMapper::map).toList()
        );
    }

    public StationEntity map(Station station) {
        return StationEntity.builder()
                .id(station.id())
                .marketId(station.marketId())
                .name(station.name())
                .arrivalDistance(station.arrivalDistance())
                .system(systemEntityMapper.map(station.system()))
                .planetary(station.planetary())
                .requireOdyssey(station.requireOdyssey())
                .fleetCarrier(station.fleetCarrier())
                .maxLandingPadSize(Optional.ofNullable(station.maxLandingPadSize()).map(LandingPadSize::name).orElse(LandingPadSize.UNKNOWN.name()))
                .marketUpdatedAt(station.marketUpdatedAt())
                .marketData(station.marketData().stream().map(marketDatumEntityMapper::map).toList())
                .build();
    }
}
