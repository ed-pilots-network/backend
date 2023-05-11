package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.Power;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.PowerMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PowerRepository implements io.edpn.backend.rest.domain.repository.system.PowerRepository {
    
    private final PowerMapper powerMapper;
    
    @Override
    public List<Power> findAll() {
        return powerMapper.findAll();
    }
    
    @Override
    public List<Power> findByNameContains(String nameSubString) {
        return powerMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Power update(Power entity) {
        powerMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public Power create(Power entity) {
        entity.setId(UUID.randomUUID());
        powerMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<Power> findById(UUID id) {
        return powerMapper.findById(id);
    }
}
