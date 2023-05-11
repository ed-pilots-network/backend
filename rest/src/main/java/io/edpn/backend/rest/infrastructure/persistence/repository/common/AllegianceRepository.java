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
    public Optional<Allegiance> findById(UUID id) {
        return allegianceMapper.findById(id);
    }
}
