package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.Security;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.SecurityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SecurityRepository implements io.edpn.backend.rest.domain.repository.system.SecurityRepository {
    
    private final SecurityMapper securityMapper;
    
    @Override
    public List<Security> findAll() {
        return securityMapper.findAll();
    }
    
    @Override
    public List<Security> findByNameContains(String nameSubString) {
        return securityMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Security update(Security entity) {
        securityMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public Security create(Security entity) {
        entity.setId(UUID.randomUUID());
        securityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<Security> findById(UUID id) {
        return securityMapper.findById(id);
    }
}
