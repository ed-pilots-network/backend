package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MybatisStationEntityMapper implements StationEntityMapper<MybatisStationEntity> {

    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;

    @Override
    public Station map(StationEntity stationEntity) {
        return new Station(
                stationEntity.getId(),
                stationEntity.getMarketId(),
                stationEntity.getName(),
                stationEntity.getType(),
                stationEntity.getDistanceFromStar(),
                Optional.ofNullable(stationEntity.getSystem()).map(systemEntityMapper::map).orElse(null),
                Optional.ofNullable(stationEntity.getLandingPads())
                        .map(Map::entrySet)
                        .map(Set::stream)
                        .map(stream -> stream
                                .collect(Collectors.toMap(
                                        k -> LandingPadSize.valueOf(k.getKey()),
                                        Map.Entry::getValue
                                )))
                        .orElse(null),
                stationEntity.getEconomies(),
                stationEntity.getEconomy(),
                stationEntity.getGovernment(),
                stationEntity.getOdyssey(),
                stationEntity.getUpdatedAt()
        );
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
