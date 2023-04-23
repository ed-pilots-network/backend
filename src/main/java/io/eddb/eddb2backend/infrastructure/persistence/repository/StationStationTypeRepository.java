package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.StationStationTypeEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.StationStationTypeEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class StationStationTypeRepository implements io.eddb.eddb2backend.domain.repository.StationStationTypeRepository {

    private final StationStationTypeEntityMapper stationStationTypeEntityMapper;

    @Override
    public StationStationTypeEntity update(StationStationTypeEntity entity) {
        stationStationTypeEntityMapper.update(entity);
        return findById(entity.getStationId())
                .orElseThrow(() -> new RuntimeException("stationStationType with StationId: " + entity.getStationId() + " could not be found after update"));
    }

    @Override
    public StationStationTypeEntity create(StationStationTypeEntity entity) {
        stationStationTypeEntityMapper.insert(entity);
        return findById(entity.getStationId())
                .orElseThrow(() -> new RuntimeException("stationStationType with StationId: " + entity.getStationId() + " could not be found after create"));
    }

    @Override
    public Optional<StationStationTypeEntity> findById(UUID id) {
        return stationStationTypeEntityMapper.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        stationStationTypeEntityMapper.delete(id);
    }
}
