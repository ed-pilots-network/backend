package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
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
class RestCommodityMarketInfoDtoMapperTest {
    
    @Mock
    private ValidatedCommodityDtoMapper commodityDtoMapper;
    
    @Mock
    private StationDtoMapper stationDtoMapper;
    
    private RestCommodityMarketInfoDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestCommodityMarketInfoDtoMapper(commodityDtoMapper, stationDtoMapper);
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnDto() {
        
        RestValidatedCommodityDto mockCommodityDto = mock(RestValidatedCommodityDto.class);
        RestStationDto mockHighestSellingStationDto = mock(RestStationDto.class);
        RestStationDto mockLowestBuyingStationDto = mock(RestStationDto.class);
        
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
        
        when(commodityDtoMapper.map(domainObject.getValidatedCommodity())).thenReturn(mockCommodityDto);
        when(stationDtoMapper.map(domainObject.getHighestSellingToStation())).thenReturn(mockHighestSellingStationDto);
        when(stationDtoMapper.map(domainObject.getLowestBuyingFromStation())).thenReturn(mockLowestBuyingStationDto);
        
        CommodityMarketInfoDto dto = underTest.map(domainObject);
        
        assertThat(dto.commodity(), is(mockCommodityDto));
        assertThat(dto.maxBuyPrice(), is(100.0));
        assertThat(dto.minBuyPrice(), is(50.0));
        assertThat(dto.avgBuyPrice(), is(75.0));
        assertThat(dto.maxSellPrice(), is(200.0));
        assertThat(dto.minSellPrice(), is(100.0));
        assertThat(dto.avgSellPrice(), is(150.0));
        assertThat(dto.minMeanPrice(), is(60.0));
        assertThat(dto.maxMeanPrice(), is(180.0));
        assertThat(dto.averageMeanPrice(), is(120.0));
        assertThat(dto.totalStock(), is(1000L));
        assertThat(dto.totalDemand(), is(500L));
        assertThat(dto.totalStations(), is(10));
        assertThat(dto.stationsWithBuyPrice(), is(5));
        assertThat(dto.stationsWithSellPrice(), is(4));
        assertThat(dto.stationsWithBuyPriceLowerThanAverage(), is(2));
        assertThat(dto.stationsWithSellPriceHigherThanAverage(), is(1));
        assertThat(dto.highestSellingToStation(), is(mockHighestSellingStationDto));
        assertThat(dto.lowestBuyingFromStation(), is(mockLowestBuyingStationDto));
        
        verify(commodityDtoMapper, times(1)).map(domainObject.getValidatedCommodity());
        verify(stationDtoMapper, times(1)).map(domainObject.getHighestSellingToStation());
        verify(stationDtoMapper, times(1)).map(domainObject.getLowestBuyingFromStation());
    }
}