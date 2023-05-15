package io.edpn.backend.rest.infrastructure.persistence.repository.common;

import io.edpn.backend.rest.domain.model.common.Government;
import io.edpn.backend.rest.domain.repository.common.GovernmentRepository;
import io.edpn.backend.rest.infrastructure.persistence.mappers.common.GovernmentMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisGovernmentRepository implements GovernmentRepository {
    
    private final GovernmentMapper governmentMapper;
    
    @Override
    public List<Government> findAll() {
        return governmentMapper.findAll();
    }
    
    @Override
    public List<Government> findByNameContains(String nameSubString) {
        return governmentMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Optional<Government> findById(UUID id) {
        return governmentMapper.findById(id);
    }
}
