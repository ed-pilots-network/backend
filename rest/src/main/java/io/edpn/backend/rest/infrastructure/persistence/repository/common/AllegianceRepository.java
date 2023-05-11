package io.edpn.backend.rest.infrastructure.persistence.repository.common;

import io.edpn.backend.rest.domain.model.common.Allegiance;
import io.edpn.backend.rest.infrastructure.persistence.mappers.common.AllegianceMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AllegianceRepository implements io.edpn.backend.rest.domain.repository.common.AllegianceRepository {
    
    private final AllegianceMapper allegianceMapper;
    
    @Override
    public List<Allegiance> findAll() {
        return allegianceMapper.findAll();
    }
    
    @Override
    public List<Allegiance> findByNameContains(String nameSubString) {
        return allegianceMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Allegiance update(Allegiance entity) {
        allegianceMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public Allegiance create(Allegiance entity) {
        entity.setId(UUID.randomUUID());
        allegianceMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<Allegiance> findById(UUID id) {
        return allegianceMapper.findById(id);
    }
}
