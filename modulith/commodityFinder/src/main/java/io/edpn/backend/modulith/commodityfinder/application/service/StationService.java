package io.edpn.backend.modulith.commodityfinder.application.service;

import io.edpn.backend.modulith.commodityfinder.domain.entity.Station;
import io.edpn.backend.modulith.commodityfinder.domain.entity.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class StationService {

    public Station getOrCreateBySystemAndStationName(System system, String name) {
        return Station.builder().build(); //TODO
    }

    public void save(Station station) {
        //TODO
    }
}
