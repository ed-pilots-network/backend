package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityMarketInfoEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisCommodityMarketInfoEntityMapperTest {
    
    @Mock
    private ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> mybatisValidatedCommodityEntityMapper;
    
    @Mock
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    
    private CommodityMarketInfoEntityMapper<MybatisCommodityMarketInfoEntity> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisCommodityMarketInfoEntityMapper(mybatisValidatedCommodityEntityMapper, mybatisStationEntityMapper);
    }
    
    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        
        ValidatedCommodity mockCommodity = mock(ValidatedCommodity.class);
        Station mockHighestSellingStation = mock(Station.class);
        Station mockLowestBuyingStation = mock(Station.class);
        
        // Setup the CommodityMarketInfoEntity with test data
        CommodityMarketInfoEntity entity = MybatisCommodityMarketInfoEntity.builder()
                .validatedCommodity(mock(MybatisValidatedCommodityEntity.class))
                .maxBuyPrice(100.0)
                .minBuyPrice(50.0)
                .avgBuyPrice(75.0)
                .maxSellPrice(200.0)
                .minSellPrice(100.0)
                .avgSellPrice(150.0)
                .minMeanPrice(60.0)
                .maxMeanPrice(180.0)
                .averageMeanPrice(120.0)
                .totalStock(1000L)
                .totalDemand(500L)
                .totalStations(10)
                .stationsWithBuyPrice(5)
                .stationsWithSellPrice(4)
                .stationsWithBuyPriceLowerThanAverage(2)
                .stationsWithSellPriceHigherThanAverage(1)
                .highestSellingToStation(mock(MybatisStationEntity.class))
                .lowestBuyingFromStation(mock(MybatisStationEntity.class))
                .build();
        
        when(mybatisValidatedCommodityEntityMapper.map(entity.getValidatedCommodity())).thenReturn(mockCommodity);
        when(mybatisStationEntityMapper.map(entity.getHighestSellingToStation())).thenReturn(mockHighestSellingStation);
        when(mybatisStationEntityMapper.map(entity.getLowestBuyingFromStation())).thenReturn(mockLowestBuyingStation);
        
        // Map the entity to a CommodityMarketInfo object
        CommodityMarketInfo result = underTest.map(entity);
        
        // Verify that the result matches the expected values
        assertThat(result.getValidatedCommodity(), is(mockCommodity));
        assertThat(result.getMaxBuyPrice(), is(100.0));
        assertThat(result.getMinBuyPrice(), is(50.0));
        assertThat(result.getAvgBuyPrice(), is(75.0));
        assertThat(result.getMaxSellPrice(), is(200.0));
        assertThat(result.getMinSellPrice(), is(100.0));
        assertThat(result.getAvgSellPrice(), is(150.0));
        assertThat(result.getMinMeanPrice(), is(60.0));
        assertThat(result.getMaxMeanPrice(), is(180.0));
        assertThat(result.getAverageMeanPrice(), is(120.0));
        assertThat(result.getTotalStock(), is(1000L));
        assertThat(result.getTotalDemand(), is(500L));
        assertThat(result.getTotalStations(), is(10));
        assertThat(result.getStationsWithBuyPrice(), is(5));
        assertThat(result.getStationsWithSellPrice(), is(4));
        assertThat(result.getStationsWithBuyPriceLowerThanAverage(), is(2));
        assertThat(result.getStationsWithSellPriceHigherThanAverage(), is(1));
        assertThat(result.getHighestSellingToStation(), is(mockHighestSellingStation));
        assertThat(result.getLowestBuyingFromStation(), is(mockLowestBuyingStation));
        
        verify(mybatisValidatedCommodityEntityMapper, times(1)).map(entity.getValidatedCommodity());
        verify(mybatisStationEntityMapper, times(1)).map(entity.getHighestSellingToStation());
        verify(mybatisStationEntityMapper, times(1)).map(entity.getLowestBuyingFromStation());
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        
        MybatisValidatedCommodityEntity mockCommodityEntity = mock(MybatisValidatedCommodityEntity.class);
        MybatisStationEntity mockHighestSellingStationEntity = mock(MybatisStationEntity.class);
        MybatisStationEntity mockLowestBuyingStationEntity = mock(MybatisStationEntity.class);
        
        // Setup the CommodityMarketInfo with test data
        CommodityMarketInfo domainObject = CommodityMarketInfo.builder()
                .validatedCommodity(mock(ValidatedCommodity.class))
                .maxBuyPrice(100.0)
                .minBuyPrice(50.0)
                .avgBuyPrice(75.0)
                .maxSellPrice(200.0)
                .minSellPrice(100.0)
                .avgSellPrice(150.0)
                .minMeanPrice(60.0)
                .maxMeanPrice(180.0)
                .averageMeanPrice(120.0)
                .totalStock(1000L)
                .totalDemand(500L)
                .totalStations(10)
                .stationsWithBuyPrice(5)
                .stationsWithSellPrice(4)
                .stationsWithBuyPriceLowerThanAverage(2)
                .stationsWithSellPriceHigherThanAverage(1)
                .highestSellingToStation(mock(Station.class))
                .lowestBuyingFromStation(mock(Station.class))
                .build();
        
        when(mybatisValidatedCommodityEntityMapper.map(domainObject.getValidatedCommodity())).thenReturn(mockCommodityEntity);
        when(mybatisStationEntityMapper.map(domainObject.getHighestSellingToStation())).thenReturn(mockHighestSellingStationEntity);
        when(mybatisStationEntityMapper.map(domainObject.getLowestBuyingFromStation())).thenReturn(mockLowestBuyingStationEntity);
        
        // Map the domainObject to a CommodityMarketInfoEntity
        CommodityMarketInfoEntity result = underTest.map(domainObject);
        
        // Verify that the result matches the expected values
        assertThat(result.getValidatedCommodity(), is(mockCommodityEntity));
        assertThat(result.getMaxBuyPrice(), is(100.0));
        assertThat(result.getMinBuyPrice(), is(50.0));
        assertThat(result.getAvgBuyPrice(), is(75.0));
        assertThat(result.getMaxSellPrice(), is(200.0));
        assertThat(result.getMinSellPrice(), is(100.0));
        assertThat(result.getAvgSellPrice(), is(150.0));
        assertThat(result.getMinMeanPrice(), is(60.0));
        assertThat(result.getMaxMeanPrice(), is(180.0));
        assertThat(result.getAverageMeanPrice(), is(120.0));
        assertThat(result.getTotalStock(), is(1000L));
        assertThat(result.getTotalDemand(), is(500L));
        assertThat(result.getTotalStations(), is(10));
        assertThat(result.getStationsWithBuyPrice(), is(5));
        assertThat(result.getStationsWithSellPrice(), is(4));
        assertThat(result.getStationsWithBuyPriceLowerThanAverage(), is(2));
        assertThat(result.getStationsWithSellPriceHigherThanAverage(), is(1));
        assertThat(result.getHighestSellingToStation(), is(mockHighestSellingStationEntity));
        assertThat(result.getLowestBuyingFromStation(), is(mockLowestBuyingStationEntity));
        
        verify(mybatisValidatedCommodityEntityMapper, times(1)).map(domainObject.getValidatedCommodity());
        verify(mybatisStationEntityMapper, times(1)).map(domainObject.getHighestSellingToStation());
        verify(mybatisStationEntityMapper, times(1)).map(domainObject.getLowestBuyingFromStation());
    }
    
}