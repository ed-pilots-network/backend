package io.edpn.backend.rest.infrastructure.persistence.repository.common;

import io.edpn.backend.rest.domain.model.common.Faction;
import io.edpn.backend.rest.domain.repository.common.FactionRepository;
import io.edpn.backend.rest.infrastructure.persistence.mappers.common.FactionMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisFactionRepository implements FactionRepository {
    
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
    public Optional<Faction> findById(UUID id) {
        return factionMapper.findById(id);
    }
}
