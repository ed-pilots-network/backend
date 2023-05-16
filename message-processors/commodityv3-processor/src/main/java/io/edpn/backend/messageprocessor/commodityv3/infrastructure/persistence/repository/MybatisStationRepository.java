package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisStationRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationRepository {

    private final IdGenerator idGenerator;
    private final StationEntityMapper stationEntityMapper;

    @Override
    public Optional<StationEntity> findByMarketId(long marketId) {
        return stationEntityMapper.findByMarketId(marketId);
    }

    @Override
    public StationEntity findOrCreateBySystemIdAndStationName(UUID systemId, String stationName) throws DatabaseEntityNotFoundException {
        return stationEntityMapper.findBySystemIdAndStationName(systemId, stationName)
                .orElseGet(() -> {
                    StationEntity s = StationEntity.builder()
                            .systemId(systemId)
                            .name(stationName)
                            .build();
                    return create(s);
                });
    }

    @Override
    public StationEntity update(StationEntity entity) throws DatabaseEntityNotFoundException {
        stationEntityMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public StationEntity create(StationEntity entity) throws DatabaseEntityNotFoundException {
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        stationEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<StationEntity> findById(UUID id) {
        return stationEntityMapper.findById(id);
    }
}
