package io.eddb.eddb2backend.infrastructure.persistence.mybatis;

import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mybatis.mappers.StationMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StationRepository implements io.eddb.eddb2backend.domain.repository.StationRepository {

    private final StationMapper stationMapper;

    public UUID save(StationEntity entity) {
        stationMapper.save(entity);
        return entity.getId();
    }

    public Optional<StationEntity> findById(UUID id) {
        return stationMapper.findById(id);
    }

    @Override
    public Optional<StationEntity> findByName(String name) {
        return stationMapper.findByName(name);
    }

    @Override
    public Collection<StationEntity> findByNameStartsWith(String name) {
        return stationMapper.findByNameStartingWith(name);
    }

}
