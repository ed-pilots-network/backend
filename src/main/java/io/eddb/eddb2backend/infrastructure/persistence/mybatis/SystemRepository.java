package io.eddb.eddb2backend.infrastructure.persistence.mybatis;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mybatis.mappers.SystemMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SystemRepository implements io.eddb.eddb2backend.domain.repository.SystemRepository {

    private final SystemMapper systemMapper;

    public UUID save(SystemEntity entity) {
        systemMapper.save(entity);
        return entity.getId();
    }

    public Optional<SystemEntity> findById(UUID id) {
        return systemMapper.findById(id);
    }

    @Override
    public Optional<SystemEntity> findByName(String name) {
        return systemMapper.findByName(name);
    }

    @Override
    public Collection<SystemEntity> findByNameStartsWith(String name) {
        return systemMapper.findByNameStartingWith(name);
    }

    public void addStation(UUID systemId, UUID stationId) {
        systemMapper.addStation(systemId, stationId);
    }
}
