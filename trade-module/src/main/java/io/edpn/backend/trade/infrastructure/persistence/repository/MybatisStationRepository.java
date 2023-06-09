package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.StationMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
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
        entity.getMarketData().forEach(marketDatumEntity -> {
            if (marketDatumEntityMapper.findById(entity.getId(), marketDatumEntity.getCommodity().getId(), marketDatumEntity.getTimestamp()).isEmpty()) {
                marketDatumEntityMapper.insert(entity.getId(), marketDatumEntity);
            } else {
                log.warn("Did not save marketDatum because of record with identical key already exists");
            }
        });
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
