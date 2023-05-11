package io.edpn.backend.rest.infrastructure.persistence.repository.common;

import io.edpn.backend.rest.domain.model.common.Economy;
import io.edpn.backend.rest.infrastructure.persistence.mappers.common.EconomyMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class EconomyRepository implements io.edpn.backend.rest.domain.repository.common.EconomyRepository {
    
    private final EconomyMapper economyMapper;
    
    @Override
    public List<Economy> findAll() {
        return economyMapper.findAll();
    }
    
    @Override
    public List<Economy> findByNameContains(String nameSubString) {
        return economyMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Optional<Economy> findById(UUID id) {
        return economyMapper.findById(id);
    }
}
