package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisStationEntityMapper implements StationEntityMapper<MybatisStationEntity> {

    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;

    @Override
    public Station map(StationEntity stationEntity) {
        return Station.builder()
                .id(stationEntity.getId())
                .marketId(stationEntity.getMarketId())
                .name(stationEntity.getName())
                .type(stationEntity.getType())
                .distanceFromStar(stationEntity.getDistanceFromStar())
                .system(Optional.ofNullable(stationEntity.getSystem()).map(systemEntityMapper::map).orElse(null))
                .landingPads(stationEntity.getLandingPads().entrySet().stream()
                        .collect(Collectors.toMap(k -> LandingPadSize.valueOf(k.getKey()), Map.Entry::getValue)))
                .economies(stationEntity.getEconomies())
                .economy(stationEntity.getEconomy())
                .government(stationEntity.getGovernment())
                .odyssey(stationEntity.getOdyssey())
                .updatedAt(stationEntity.getUpdatedAt())
                .build();
    }

    @Override
    public MybatisStationEntity map(Station station) {
        return MybatisStationEntity.builder()
                .id(station.id())
                .marketId(station.marketId())
                .name(station.name())
                .type(station.type())
                .distanceFromStar(station.distanceFromStar())
                .system(Optional.ofNullable(station.system()).map(systemEntityMapper::map).orElse(null))
                .landingPads(station.landingPads().entrySet().stream()
                        .collect(Collectors.toMap(k -> k.getKey().name(), Map.Entry::getValue)))
                .economies(station.economies())
                .economy(station.economy())
                .government(station.government())
                .odyssey(station.odyssey())
                .updatedAt(station.updatedAt())
                .build();
    }
}
