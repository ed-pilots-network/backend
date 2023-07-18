package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.MarketDatum;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.infrastructure.persistence.entity.MarketDatumEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.SystemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StationMapperTest {

    @Mock
    private SystemMapper systemMapper;

    @Mock
    private MarketDatumMapper marketDatumMapper;

    private StationMapper underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationMapper(systemMapper, marketDatumMapper);
    }

    @Test
    public void shouldMapStationEntityToStation() {
        // set up mock objects
        MarketDatumEntity mockMarketDatumEntity = mock(MarketDatumEntity.class);
        MarketDatum mockMarketDatum = mock(MarketDatum.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System mockSystem = mock(System.class);

        UUID id = UUID.randomUUID();
        Long marketId = 12345L;
        String name = "Station Name";
        Double arrivalDistance = 123.45;
        Boolean planetary = true;
        Boolean requireOdyssey = true;
        Boolean fleetCarrier = true;
        String maxLandingPadSize = "LARGE";
        LocalDateTime marketUpdatedAt = LocalDateTime.now();
        List<MarketDatumEntity> marketDataEntity = Collections.singletonList(mockMarketDatumEntity);
        StationEntity stationEntity = StationEntity.builder()
                .id(id)
                .marketId(marketId)
                .name(name)
                .arrivalDistance(arrivalDistance)
                .system(mockSystemEntity)
                .planetary(planetary)
                .requireOdyssey(requireOdyssey)
                .fleetCarrier(fleetCarrier)
                .maxLandingPadSize(maxLandingPadSize)
                .marketUpdatedAt(marketUpdatedAt)
                .marketData(marketDataEntity)
                .build();

        when(systemMapper.map(mockSystemEntity)).thenReturn(mockSystem);
        when(marketDatumMapper.map(marketDataEntity)).thenReturn(Collections.singletonList(mockMarketDatum));

        Station station = underTest.map(stationEntity);

        assertThat(station.getId(), is(id));
        assertThat(station.getMarketId(), is(marketId));
        assertThat(station.getName(), is(name));
        assertThat(station.getArrivalDistance(), is(arrivalDistance));
        assertThat(station.getPlanetary(), is(planetary));
        assertThat(station.getRequireOdyssey(), is(requireOdyssey));
        assertThat(station.getFleetCarrier(), is(fleetCarrier));
        assertThat(station.getMaxLandingPadSize(), is(LandingPadSize.LARGE));
        assertThat(station.getMarketUpdatedAt(), is(marketUpdatedAt));

        verify(systemMapper, times(1)).map(mockSystemEntity);
        verify(marketDatumMapper, times(1)).map(marketDataEntity);
    }

    @Test
    public void shouldMapStationToStationEntity() {
        // set up mock objects
        MarketDatum mockMarketDatum = mock(MarketDatum.class);
        MarketDatumEntity mockMarketDatumEntity = mock(MarketDatumEntity.class);
        System mockSystem = mock(System.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);

        UUID id = UUID.randomUUID();
        Long marketId = 12345L;
        String name = "Station Name";
        Double arrivalDistance = 123.45;
        Boolean planetary = true;
        Boolean requireOdyssey = true;
        Boolean fleetCarrier = true;
        LandingPadSize maxLandingPadSize = LandingPadSize.LARGE;
        LocalDateTime marketUpdatedAt = LocalDateTime.now();
        List<MarketDatum> marketData = Collections.singletonList(mockMarketDatum);

        Station station = Station.builder()
                .id(id)
                .marketId(marketId)
                .name(name)
                .arrivalDistance(arrivalDistance)
                .system(mockSystem)
                .planetary(planetary)
                .requireOdyssey(requireOdyssey)
                .fleetCarrier(fleetCarrier)
                .maxLandingPadSize(maxLandingPadSize)
                .marketUpdatedAt(marketUpdatedAt)
                .marketData(marketData)
                .build();

        when(systemMapper.map(mockSystem)).thenReturn(mockSystemEntity);
        when(marketDatumMapper.mapToEntity(marketData)).thenReturn(Collections.singletonList(mockMarketDatumEntity));

        StationEntity stationEntity = underTest.map(station);

        assertThat(stationEntity.getId(), is(id));
        assertThat(stationEntity.getMarketId(), is(marketId));
        assertThat(stationEntity.getName(), is(name));
        assertThat(stationEntity.getArrivalDistance(), is(arrivalDistance));
        assertThat(stationEntity.getPlanetary(), is(planetary));
        assertThat(stationEntity.getRequireOdyssey(), is(requireOdyssey));
        assertThat(stationEntity.getFleetCarrier(), is(fleetCarrier));
        assertThat(stationEntity.getMaxLandingPadSize(), is(maxLandingPadSize.name()));
        assertThat(stationEntity.getMarketUpdatedAt(), is(marketUpdatedAt));

        verify(systemMapper, times(1)).map(mockSystem);
        verify(marketDatumMapper, times(1)).mapToEntity(marketData);
    }
}
