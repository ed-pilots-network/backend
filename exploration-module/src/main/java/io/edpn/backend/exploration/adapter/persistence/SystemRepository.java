package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemsByNameContainingPort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveSystemPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemRepository implements SaveSystemPort, LoadSystemPort, LoadSystemsByNameContainingPort {

    private final MybatisSystemRepository mybatisSystemRepository;
    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;

    @Override
    public Optional<System> load(String name) {
        return mybatisSystemRepository.findByName(name)
                .map(systemEntityMapper::map);
    }

    @Override
    public System save(System system) {
        return systemEntityMapper.map(mybatisSystemRepository.insertOrUpdateOnConflict(systemEntityMapper.map(system)));
    }

    @Override
    public List<System> loadByNameContaining(String name, int amount) {
        return mybatisSystemRepository.findFromSearchbar(name, amount).stream()
                .map(systemEntityMapper::map)
                .toList();
    }
}
