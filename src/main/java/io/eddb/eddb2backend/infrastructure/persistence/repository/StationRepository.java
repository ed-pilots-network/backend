package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.StationEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class StationRepository implements io.eddb.eddb2backend.domain.repository.StationRepository {

    private final StationEntityMapper stationEntityMapper;

    @Override
    public StationEntity findOrCreateByMarketId(long marketId) {
        return stationEntityMapper.findByMarketId(marketId)
                .orElseGet(() -> {
                    StationEntity s = StationEntity.builder()
                            .edMarketId(marketId)
                            .build();
                    return create(s);
                });
    }

    @Override
    public StationEntity update(StationEntity entity) {
        stationEntityMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("station with id: " + entity.getId().getValue() + " could not be found after update"));
    }

    @Override
    public StationEntity create(StationEntity entity) {
        entity.setId(new StationEntity.Id(UUID.randomUUID()));
        stationEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("station with id: " + entity.getId().getValue() + " could not be found after create"));
    }

    @Override
    public Optional<StationEntity> findById(StationEntity.Id id) {
        return stationEntityMapper.findById(id.getValue());
    }
}
