package io.edpn.backend.modulith.commodityfinder.application.service;

import io.edpn.backend.modulith.commodityfinder.application.mappers.persistence.SystemMapper;
import io.edpn.backend.modulith.commodityfinder.domain.entity.System;
import io.edpn.backend.modulith.commodityfinder.domain.repository.SystemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SystemService {

    private final SystemRepository systemRepository;
    private final SystemMapper systemMapper;

    public System getOrCreateByName(String name) {
        return systemMapper.map(systemRepository.findOrCreateByName(name));
    }
}
