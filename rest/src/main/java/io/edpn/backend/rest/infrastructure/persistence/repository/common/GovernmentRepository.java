package io.edpn.backend.rest.infrastructure.persistence.repository.common;

import io.edpn.backend.rest.domain.model.common.Government;
import io.edpn.backend.rest.infrastructure.persistence.mappers.common.GovernmentMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GovernmentRepository implements io.edpn.backend.rest.domain.repository.common.GovernmentRepository {
    
    private final GovernmentMapper governmentMapper;
    
    @Override
    public List<Government> findAll() {
        return governmentMapper.findAll();
    }
    
    @Override
    public List<Government> findByNameContains(String nameSubString) {
        return governmentMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Government update(Government entity) {
        governmentMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public Government create(Government entity) {
        entity.setId(UUID.randomUUID());
        governmentMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<Government> findById(UUID id) {
        return governmentMapper.findById(id);
    }
}
