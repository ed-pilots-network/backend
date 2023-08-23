package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.StationEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.MarketDatumEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
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
class MybatisStationEntityMapperTest {
    
    @Mock
    private SystemEntityMapper<MybatisSystemEntity> mybatisSystemEntityMapper;
    
    @Mock
    private MarketDatumEntityMapper<MybatisMarketDatumEntity> mybatisMarketDatumEntityMapper;
    
    private StationEntityMapper<MybatisStationEntity> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisStationEntityMapper(mybatisSystemEntityMapper, mybatisMarketDatumEntityMapper);
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
        
        StationEntity entity = MybatisStationEntity.builder()
                .id(id)
                .marketId(marketId)
                .name(name)
                .arrivalDistance(arrivalDistance)
                .system(mock(MybatisSystemEntity.class))
                .planetary(planetary)
                .requireOdyssey(requireOdyssey)
                .fleetCarrier(fleetCarrier)
                .maxLandingPadSize(maxLandingPadSize)
                .marketUpdatedAt(marketUpdatedAt)
                .marketData(List.of(mock(MybatisMarketDatumEntity.class)))
                .build();
        
        when(mybatisSystemEntityMapper.map(entity.getSystem())).thenReturn(mockSystem);
        when(mybatisMarketDatumEntityMapper.map(entity.getMarketData().get(0))).thenReturn(mockMarketDatum);
        
        Station domainObject = underTest.map(entity);
        
        assertThat(domainObject.getId(), is(id));
        assertThat(domainObject.getMarketId(), is(marketId));
        assertThat(domainObject.getName(), is(name));
        assertThat(domainObject.getArrivalDistance(), is(arrivalDistance));
        assertThat(domainObject.getPlanetary(), is(planetary));
        assertThat(domainObject.getRequireOdyssey(), is(requireOdyssey));
        assertThat(domainObject.getFleetCarrier(), is(fleetCarrier));
        assertThat(domainObject.getMaxLandingPadSize(), is(LandingPadSize.LARGE));
        assertThat(domainObject.getMarketUpdatedAt(), is(marketUpdatedAt));
        
        verify(mybatisSystemEntityMapper, times(1)).map(entity.getSystem());
        verify(mybatisMarketDatumEntityMapper, times(1)).map(entity.getMarketData().get(0));
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        
        // set up mock objects
        MybatisMarketDatumEntity mockMarketDatumEntity = mock(MybatisMarketDatumEntity.class);
        MybatisSystemEntity mockSystemEntity = mock(MybatisSystemEntity.class);
        
        UUID id = UUID.randomUUID();
        Long marketId = 12345L;
        String name = "Station Name";
        Double arrivalDistance = 123.45;
        Boolean planetary = true;
        Boolean requireOdyssey = true;
        Boolean fleetCarrier = true;
        String maxLandingPadSize = "LARGE";
        LocalDateTime marketUpdatedAt = LocalDateTime.now();
        
        Station domainObject = Station.builder()
                .id(id)
                .marketId(marketId)
                .name(name)
                .arrivalDistance(arrivalDistance)
                .system(mock(System.class))
                .planetary(planetary)
                .requireOdyssey(requireOdyssey)
                .fleetCarrier(fleetCarrier)
                .maxLandingPadSize(LandingPadSize.valueOf(maxLandingPadSize))
                .marketUpdatedAt(marketUpdatedAt)
                .marketData(List.of(mock(MarketDatum.class)))
                .build();
        
        when(mybatisSystemEntityMapper.map(domainObject.getSystem())).thenReturn(mockSystemEntity);
        when(mybatisMarketDatumEntityMapper.map(domainObject.getMarketData().get(0))).thenReturn(mockMarketDatumEntity);
        
        MybatisStationEntity entity = underTest.map(domainObject);
        
        assertThat(entity.getId(), is(id));
        assertThat(entity.getMarketId(), is(marketId));
        assertThat(entity.getName(), is(name));
        assertThat(entity.getArrivalDistance(), is(arrivalDistance));
        assertThat(entity.getPlanetary(), is(planetary));
        assertThat(entity.getRequireOdyssey(), is(requireOdyssey));
        assertThat(entity.getFleetCarrier(), is(fleetCarrier));
        assertThat(entity.getMaxLandingPadSize(), is(maxLandingPadSize));
        assertThat(entity.getMarketUpdatedAt(), is(marketUpdatedAt));
        
        verify(mybatisSystemEntityMapper, times(1)).map(domainObject.getSystem());
        verify(mybatisMarketDatumEntityMapper, times(1)).map(domainObject.getMarketData().get(0));
    }
}