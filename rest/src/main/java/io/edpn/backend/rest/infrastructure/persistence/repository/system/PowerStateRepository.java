package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.PowerState;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.PowerStateMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PowerStateRepository implements io.edpn.backend.rest.domain.repository.system.PowerStateRepository {
    
    private final PowerStateMapper powerStateMapper;
    
    @Override
    public List<PowerState> findAll() {
        return powerStateMapper.findAll();
    }
    
    @Override
    public List<PowerState> findByNameContains(String nameSubString) {
        return powerStateMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public PowerState update(PowerState entity) {
        powerStateMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public PowerState create(PowerState entity) {
        entity.setId(UUID.randomUUID());
        powerStateMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<PowerState> findById(UUID id) {
        return powerStateMapper.findById(id);
    }
}
