package io.eddb.eddb2backend.integration;

import io.eddb.eddb2backend.domain.model.body.Body;
import io.eddb.eddb2backend.domain.model.common.Allegiance;
import io.eddb.eddb2backend.domain.model.station.LandingPad;
import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.domain.model.system.System;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresStationRepository;

import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StationRepositoryTest {
    
    @Autowired
    private PostgresStationRepository postgresStationRepository;
    
    @Test
    void findByNameIgnoreCaseTest(){
        Station station =  Station.builder()
                .id(1L)
                .name("TestStation")
                .lastUpdated(LocalDateTime.now())
                .distanceToStar(300L)
                .hasBlackMarket(true)
                .hasMarket(true)
                .hasRefuel(true)
                .hasRepair(true)
                .hasRearm(true)
                .hasOutfitting(true)
                .hasShipyard(true)
                .hasDocking(true)
                .hasCommodities(true)
                .hasMaterialTrader(true)
                .hasTechnologyBroker(true)
                .hasCarrierVendor(true)
                .hasCarrierAdministration(true)
                .hasUniversalCartographics(true)
                .isPlanetary(true)
                .shipYardUpdatedAt(LocalDateTime.now())
                .outfittingUpdatedAt(LocalDateTime.now())
                .marketUpdatedAt(LocalDateTime.now())
                .edMarketId(2L)
                .maxLandingPadSize(new LandingPad(3L, 'M'))
                .allegiance(new Allegiance(4L, "IMPERIAL"))
                .body(new Body(5L, "Earth", System.builder().id(6L).name("Sol").build()))
                .build();
        
        java.lang.System.out.println(station);
        java.lang.System.out.println(StationEntity.Mapper.map(station));

        postgresStationRepository.save(StationEntity.Mapper.map(station).orElse(new StationEntity()));

        Station returnedStation = StationEntity.Mapper.map(postgresStationRepository.findByNameContainingIgnoreCase("teststation").iterator().next()).orElse(null);

        Assertions.assertEquals(station, returnedStation);
    }
}
