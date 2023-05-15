package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.PowerState;
import io.edpn.backend.rest.domain.repository.system.PowerStateRepository;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.PowerStateMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisPowerStateRepository implements PowerStateRepository {
    
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
    public Optional<PowerState> findById(UUID id) {
        return powerStateMapper.findById(id);
    }
}
