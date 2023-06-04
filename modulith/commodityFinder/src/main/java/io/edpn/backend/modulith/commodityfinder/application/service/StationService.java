package io.edpn.backend.modulith.commodityfinder.application.service;

import io.edpn.backend.modulith.commodityfinder.application.mappers.persistence.StationMapper;
import io.edpn.backend.modulith.commodityfinder.application.mappers.persistence.SystemMapper;
import io.edpn.backend.modulith.commodityfinder.domain.entity.Station;
import io.edpn.backend.modulith.commodityfinder.domain.entity.System;
import io.edpn.backend.modulith.commodityfinder.domain.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
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
