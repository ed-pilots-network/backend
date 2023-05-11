package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.PowerState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PowerStateRepository {
    List<PowerState> findAll();
    
    List<PowerState> findByNameContains(String nameSubString);
    
    Optional<PowerState> findById(UUID id);
}
