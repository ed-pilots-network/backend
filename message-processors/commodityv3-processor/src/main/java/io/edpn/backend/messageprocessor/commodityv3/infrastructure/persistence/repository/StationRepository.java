package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class StationRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationRepository {

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
                .orElseThrow(() -> new RuntimeException("station with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public StationEntity create(StationEntity entity) {
        entity.setId(UUID.randomUUID());
        stationEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("station with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<StationEntity> findById(UUID id) {
        return stationEntityMapper.findById(id);
    }
}
