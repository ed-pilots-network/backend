package io.edpn.backend.rest.infrastructure.persistence.repository.common;

import io.edpn.backend.rest.domain.model.common.Faction;
import io.edpn.backend.rest.infrastructure.persistence.mappers.common.FactionMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FactionRepository implements io.edpn.backend.rest.domain.repository.common.FactionRepository {
    
    private final FactionMapper factionMapper;
    
    @Override
    public List<Faction> findAll() {
        return factionMapper.findAll();
    }
    
    @Override
    public List<Faction> findByNameContains(String nameSubString) {
        return factionMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Faction update(Faction entity) {
        factionMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public Faction create(Faction entity) {
        entity.setId(UUID.randomUUID());
        factionMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<Faction> findById(UUID id) {
        return factionMapper.findById(id);
    }
}
