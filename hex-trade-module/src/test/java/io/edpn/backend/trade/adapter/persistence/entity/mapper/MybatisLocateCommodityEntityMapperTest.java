package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisLocateCommodityEntityMapperTest {
    
    @Mock
    private ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> mybatisValidatedCommodityEntityMapper;
    
    @Mock
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    
    private LocateCommodityEntityMapper<MybatisLocateCommodityEntity> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisLocateCommodityEntityMapper(mybatisValidatedCommodityEntityMapper, mybatisStationEntityMapper);
    }
    
    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        // create mock objects
        ValidatedCommodity mockCommodity = mock(ValidatedCommodity.class);
        Station mockStation = mock(Station.class);
        LocalDateTime pricesUpdatedAt = LocalDateTime.now();
        
        // Setup the LocateCommodity Entity with test data
        LocateCommodityEntity entity = MybatisLocateCommodityEntity.builder()
                .priceUpdatedAt(pricesUpdatedAt)
                .validatedCommodity(mock(MybatisValidatedCommodityEntity.class))
                .station(mock(MybatisStationEntity.class))
                .supply(100L)
                .demand(200L)
                .buyPrice(1234L)
                .sellPrice(4321L)
                .distance(63.5)
                .build();
        
        when(mybatisValidatedCommodityEntityMapper.map(entity.getValidatedCommodity())).thenReturn(mockCommodity);
        when(mybatisStationEntityMapper.map(entity.getStation())).thenReturn(mockStation);
        
        // Map the entity to a CommodityMarketInfo object
        LocateCommodity result = underTest.map(entity);
        
        // Verify that the result matches the expected values
        assertThat(result.getPriceUpdatedAt(), is(pricesUpdatedAt));
        assertThat(result.getValidatedCommodity(), is(mockCommodity));
        assertThat(result.getStation(), is(mockStation));
        assertThat(result.getSupply(), is(100L));
        assertThat(result.getDemand(), is(200L));
        assertThat(result.getBuyPrice(), is(1234L));
        assertThat(result.getSellPrice(), is(4321L));
        assertThat(result.getDistance(), is(63.5));
        
        verify(mybatisValidatedCommodityEntityMapper, times(1)).map(entity.getValidatedCommodity());
        verify(mybatisStationEntityMapper, times(1)).map(entity.getStation());
        
        
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        // create mock objects
        MybatisValidatedCommodityEntity mockCommodityEntity = mock(MybatisValidatedCommodityEntity.class);
        MybatisStationEntity mockStationEntity = mock(MybatisStationEntity.class);
        LocalDateTime pricesUpdatedAt = LocalDateTime.now();
        
        // Setup the LocateCommodity Object with test data
        LocateCommodity domainObject = LocateCommodity.builder()
                .priceUpdatedAt(pricesUpdatedAt)
                .validatedCommodity(mock(ValidatedCommodity.class))
                .station(mock(Station.class))
                .supply(100L)
                .demand(200L)
                .buyPrice(1234L)
                .sellPrice(4321L)
                .distance(63.5)
                .build();
        
        when(mybatisValidatedCommodityEntityMapper.map(domainObject.getValidatedCommodity())).thenReturn(mockCommodityEntity);
        when(mybatisStationEntityMapper.map(domainObject.getStation())).thenReturn(mockStationEntity);
        
        // Map the entity to a CommodityMarketInfo object
        MybatisLocateCommodityEntity result = underTest.map(domainObject);
        
        // Verify that the result matches the expected values
        assertThat(result.getPriceUpdatedAt(), is(pricesUpdatedAt));
        assertThat(result.getValidatedCommodity(), is(mockCommodityEntity));
        assertThat(result.getStation(), is(mockStationEntity));
        assertThat(result.getSupply(), is(100L));
        assertThat(result.getDemand(), is(200L));
        assertThat(result.getBuyPrice(), is(1234L));
        assertThat(result.getSellPrice(), is(4321L));
        assertThat(result.getDistance(), is(63.5));
        
        verify(mybatisValidatedCommodityEntityMapper, times(1)).map(domainObject.getValidatedCommodity());
        verify(mybatisStationEntityMapper, times(1)).map(domainObject.getStation());
        
    }
    
}