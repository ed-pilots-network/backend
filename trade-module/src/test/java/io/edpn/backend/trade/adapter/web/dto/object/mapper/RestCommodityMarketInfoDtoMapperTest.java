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

        CommodityMarketInfo domainObject = new CommodityMarketInfo(
                mock(ValidatedCommodity.class),
                100.0,
                50.0,
                75.0,
                200.0,
                100.0,
                150.0,
                60.0,
                180.0,
                120.0,
                1000L,
                500L,
                10,
                5,
                4,
                2,
                1,
                mock(Station.class),
                mock(Station.class)
        );

        when(commodityDtoMapper.map(domainObject.validatedCommodity())).thenReturn(mockCommodityDto);
        when(stationDtoMapper.map(domainObject.highestSellingToStation())).thenReturn(mockHighestSellingStationDto);
        when(stationDtoMapper.map(domainObject.lowestBuyingFromStation())).thenReturn(mockLowestBuyingStationDto);
        
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

        verify(commodityDtoMapper, times(1)).map(domainObject.validatedCommodity());
        verify(stationDtoMapper, times(1)).map(domainObject.highestSellingToStation());
        verify(stationDtoMapper, times(1)).map(domainObject.lowestBuyingFromStation());
    }
}