package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntityMapper;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemsByNameContainingPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemRepository implements CreateSystemPort, LoadSystemPort, SaveSystemPort, LoadSystemsByNameContainingPort {

    private final MybatisSystemRepository mybatisSystemRepository;
    private final SystemEntityMapper systemEntityMapper;
    private final IdGenerator idGenerator;

    @Override
    public System create(String systemName) throws DatabaseEntityNotFoundException {
        mybatisSystemRepository.insert(SystemEntity.builder()
                .id(idGenerator.generateId())
                .name(systemName)
                .build());
        return load(systemName)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("System with name '" + systemName + "' could not be found after create"));

    }

    @Override
    public Optional<System> load(String name) {
        return mybatisSystemRepository.findByName(name)
                .map(systemEntityMapper::map);
    }

    @Override
    public System save(System system) {
        mybatisSystemRepository.update(systemEntityMapper.map(system));

        return mybatisSystemRepository.findById(system.getId())
                .map(systemEntityMapper::map)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("System '" + system + "' could not be found after update"));
    }

    @Override
    public List<System> load(String name, int amount) {
        return mybatisSystemRepository.findFromSearchbar(name, amount).stream()
                .map(systemEntityMapper::map)
                .toList();
    }
}
