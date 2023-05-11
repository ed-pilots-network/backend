package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.ReserveType;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.ReserveTypeMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ReserveTypeRepository implements io.edpn.backend.rest.domain.repository.system.ReserveTypeRepository {
    
    private final ReserveTypeMapper reserveTypeMapper;
    
    @Override
    public List<ReserveType> findAll() {
        return reserveTypeMapper.findAll();
    }
    
    @Override
    public List<ReserveType> findByNameContains(String nameSubString) {
        return reserveTypeMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Optional<ReserveType> findById(UUID id) {
        return reserveTypeMapper.findById(id);
    }
}
