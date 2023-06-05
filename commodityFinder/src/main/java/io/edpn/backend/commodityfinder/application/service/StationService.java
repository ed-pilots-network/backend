package io.edpn.backend.commodityfinder.application.service;

import io.edpn.backend.commodityfinder.application.mappers.persistence.StationMapper;
import io.edpn.backend.commodityfinder.application.mappers.persistence.SystemMapper;
import io.edpn.backend.commodityfinder.domain.entity.Station;
import io.edpn.backend.commodityfinder.domain.entity.System;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
public class StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;
    private final SystemMapper systemMapper;

    public Station getOrCreateBySystemAndStationName(System system, String name) {
        return stationMapper.map(stationRepository.findOrCreateBySystemAndStationName(systemMapper.map(system), name));
    }

    public void save(Station station) {
        stationRepository.update(stationMapper.map(station));
    }
}
