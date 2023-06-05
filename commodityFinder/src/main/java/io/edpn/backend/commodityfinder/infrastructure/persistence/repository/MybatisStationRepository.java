package io.edpn.backend.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.dto.StationEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.StationMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisStationRepository implements StationRepository {

    private final IdGenerator idGenerator;
    private final StationMapper stationMapper;
    private final StationEntityMapper stationEntityMapper;
    private final MarketDatumEntityMapper marketDatumEntityMapper;

    @Override
    public Station findOrCreateBySystemAndStationName(System system, String stationName) throws DatabaseEntityNotFoundException {
        return stationEntityMapper.findBySystemIdAndStationName(system.getId(), stationName)
                .map(stationMapper::map)
                .orElseGet(() -> {
                    Station s = Station.builder()
                            .system(system)
                            .name(stationName)
                            .build();
                    return create(s);
                });
    }

    @Override
    public Station update(Station station) throws DatabaseEntityNotFoundException {
        var entity = stationMapper.map(station);
        stationEntityMapper.update(entity);

        saveMarketData(entity);

        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after update"));
    }

    private void saveMarketData(StationEntity entity) {
        marketDatumEntityMapper.deleteByStationId(entity.getId());
        entity.getMarketData().forEach(marketDatumEntity -> marketDatumEntityMapper.insert(entity.getId(), marketDatumEntity));
    }

    @Override
    public Station create(Station station) throws DatabaseEntityNotFoundException {
        var entity = stationMapper.map(station);
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        stationEntityMapper.insert(entity);
        saveMarketData(entity);

        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<Station> findById(UUID id) {
        return stationEntityMapper.findById(id)
                .map(stationMapper::map);
    }
}
