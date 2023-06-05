package io.edpn.backend.commodityfinder.application.service;

import io.edpn.backend.commodityfinder.application.mappers.persistence.SystemMapper;
import io.edpn.backend.commodityfinder.domain.entity.System;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
public class SystemService {

    private final SystemRepository systemRepository;
    private final SystemMapper systemMapper;

    public System getOrCreateByName(String name) {
        return systemMapper.map(systemRepository.findOrCreateByName(name));
    }
}
