package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.persistence.entity.StationEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.MarketDatumEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MybatisStationEntityMapper implements StationEntityMapper<MybatisStationEntity> {
    
    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;
    private final MarketDatumEntityMapper<MybatisMarketDatumEntity> marketDatumEntityMapper;
    
    @Override
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
    
    @Override
    public MybatisStationEntity map(Station station) {
        return MybatisStationEntity.builder()
                .id(station.getId())
                .marketId(station.getMarketId())
                .name(station.getName())
                .arrivalDistance(station.getArrivalDistance())
                .system(systemEntityMapper.map(station.getSystem()))
                .planetary(station.getPlanetary())
                .requireOdyssey(station.getRequireOdyssey())
                .fleetCarrier(station.getFleetCarrier())
                .maxLandingPadSize(Optional.ofNullable(station.getMaxLandingPadSize()).map(LandingPadSize::name).orElse(LandingPadSize.UNKNOWN.name()))
                .marketUpdatedAt(station.getMarketUpdatedAt())
                .marketData(station.getMarketData().stream().map(marketDatumEntityMapper::map).toList())
                .build();
    }
}
