package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MarketDatumEntity;
import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationEntityMapperTest {

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private MarketDatumEntityMapper marketDatumEntityMapper;

    private StationEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationEntityMapper(systemEntityMapper, marketDatumEntityMapper);
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {

        // set up mock objects
        MarketDatum mockMarketDatum = mock(MarketDatum.class);
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

        StationEntity entity = StationEntity.builder()
                .id(id)
                .marketId(marketId)
                .name(name)
                .arrivalDistance(arrivalDistance)
                .system(mock(SystemEntity.class))
                .planetary(planetary)
                .requireOdyssey(requireOdyssey)
                .fleetCarrier(fleetCarrier)
                .maxLandingPadSize(maxLandingPadSize)
                .marketUpdatedAt(marketUpdatedAt)
                .marketData(List.of(mock(MarketDatumEntity.class)))
                .build();

        when(systemEntityMapper.map(entity.getSystem())).thenReturn(mockSystem);
        when(marketDatumEntityMapper.map(entity.getMarketData().get(0))).thenReturn(mockMarketDatum);

        Station domainObject = underTest.map(entity);

        assertThat(domainObject.id(), is(id));
        assertThat(domainObject.marketId(), is(marketId));
        assertThat(domainObject.name(), is(name));
        assertThat(domainObject.arrivalDistance(), is(arrivalDistance));
        assertThat(domainObject.planetary(), is(planetary));
        assertThat(domainObject.requireOdyssey(), is(requireOdyssey));
        assertThat(domainObject.fleetCarrier(), is(fleetCarrier));
        assertThat(domainObject.maxLandingPadSize(), is(LandingPadSize.LARGE));
        assertThat(domainObject.marketUpdatedAt(), is(marketUpdatedAt));

        verify(systemEntityMapper, times(1)).map(entity.getSystem());
        verify(marketDatumEntityMapper, times(1)).map(entity.getMarketData().get(0));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        // set up mock objects
        MarketDatumEntity mockMarketDatumEntity = mock(MarketDatumEntity.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);

        UUID id = UUID.randomUUID();
        Long marketId = 12345L;
        String name = "Station Name";
        Double arrivalDistance = 123.45;
        Boolean planetary = true;
        Boolean requireOdyssey = true;
        Boolean fleetCarrier = true;
        String maxLandingPadSize = "LARGE";
        LocalDateTime marketUpdatedAt = LocalDateTime.now();

        Station domainObject = new Station(
                id,
                marketId,
                name,
                arrivalDistance,
                mock(System.class),
                planetary,
                requireOdyssey,
                fleetCarrier,
                LandingPadSize.valueOf(maxLandingPadSize),
                marketUpdatedAt,
                List.of(mock(MarketDatum.class))
        );

        when(systemEntityMapper.map(domainObject.system())).thenReturn(mockSystemEntity);
        when(marketDatumEntityMapper.map(domainObject.marketData().get(0))).thenReturn(mockMarketDatumEntity);

        StationEntity entity = underTest.map(domainObject);

        assertThat(entity.getId(), is(id));
        assertThat(entity.getMarketId(), is(marketId));
        assertThat(entity.getName(), is(name));
        assertThat(entity.getArrivalDistance(), is(arrivalDistance));
        assertThat(entity.getPlanetary(), is(planetary));
        assertThat(entity.getRequireOdyssey(), is(requireOdyssey));
        assertThat(entity.getFleetCarrier(), is(fleetCarrier));
        assertThat(entity.getMaxLandingPadSize(), is(maxLandingPadSize));
        assertThat(entity.getMarketUpdatedAt(), is(marketUpdatedAt));

        verify(systemEntityMapper, times(1)).map(domainObject.system());
        verify(marketDatumEntityMapper, times(1)).map(domainObject.marketData().get(0));
    }
}