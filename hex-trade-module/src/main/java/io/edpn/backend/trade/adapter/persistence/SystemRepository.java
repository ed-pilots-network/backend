package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.system.CreateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemByIdPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class SystemRepository implements CreateSystemPort, LoadOrCreateSystemByNamePort, LoadSystemByIdPort, UpdateSystemPort, LoadSystemsByFilterPort {

    private final IdGenerator idGenerator;
    private final SystemEntityMapper<MybatisSystemEntity> mybatisSystemEntityMapper;
    private final MybatisPersistenceFindSystemFilterMapper mybatisPersistenceFindSystemFilterMapper;
    private final MybatisSystemRepository mybatisSystemRepository;

    @Override
    public System create(System system) {
        var entity = mybatisSystemEntityMapper.map(system);

        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }

        mybatisSystemRepository.insert(entity);
        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public System loadOrCreateSystemByName(String systemName) {
        return mybatisSystemRepository.findByName(systemName)
                .map(mybatisSystemEntityMapper::map)
                .orElseGet(() -> {
                    System s = System.builder()
                            .name(systemName)
                            .build();

                    return create(s);
                });
    }

    @Override
    public Optional<System> loadById(UUID uuid) {
        return mybatisSystemRepository.findById(uuid)
                .map(mybatisSystemEntityMapper::map);
    }

    @Override
    public System update(System system) {
        var entity = mybatisSystemEntityMapper.map(system);

        mybatisSystemRepository.update(entity);
        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public List<System> loadByFilter(FindSystemFilter findSystemFilter) {
        return mybatisSystemRepository.findByFilter(mybatisPersistenceFindSystemFilterMapper.map(findSystemFilter))
                .stream()
                .map(mybatisSystemEntityMapper::map)
                .toList();
    }
}
