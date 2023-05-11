package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.Security;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SecurityRepository {
    List<Security> findAll();
    
    List<Security> findByNameContains(String nameSubString);
    
    Optional<Security> findById(UUID id);
}
