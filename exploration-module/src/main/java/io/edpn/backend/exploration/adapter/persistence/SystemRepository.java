package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.system.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemsByNameContainingPort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveSystemPort;
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
    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;
    private final IdGenerator idGenerator;

    @Override
    public System create(String systemName) throws DatabaseEntityNotFoundException {
        mybatisSystemRepository.insert(new MybatisSystemEntity(idGenerator.generateId(), systemName, null, null, null, null,null));
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

        return mybatisSystemRepository.findById(system.id())
                .map(systemEntityMapper::map)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("System '" + system + "' could not be found after update"));
    }

    @Override
    public List<System> loadByNameContaining(String name, int amount) {
        return mybatisSystemRepository.findFromSearchbar(name, amount).stream()
                .map(systemEntityMapper::map)
                .toList();
    }
}
