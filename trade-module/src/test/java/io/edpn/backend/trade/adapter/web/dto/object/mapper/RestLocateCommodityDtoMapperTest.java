package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
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
class RestLocateCommodityDtoMapperTest {
    
    @Mock
    private StationDtoMapper stationDtoMapper;
    
    @Mock
    private ValidatedCommodityDtoMapper commodityDtoMapper;
    
    private RestLocateCommodityDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestLocateCommodityDtoMapper(stationDtoMapper, commodityDtoMapper);
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnDto() {
        RestValidatedCommodityDto mockCommodityDto = mock(RestValidatedCommodityDto.class);
        RestStationDto mockStationDto = mock(RestStationDto.class);
        LocalDateTime pricesUpdatedAt = LocalDateTime.now();
        
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
        
        when(commodityDtoMapper.map(domainObject.getValidatedCommodity())).thenReturn(mockCommodityDto);
        when(stationDtoMapper.map(domainObject.getStation())).thenReturn(mockStationDto);
        
        LocateCommodityDto result = underTest.map(domainObject);
        
        assertThat(result.priceUpdatedAt(), is(pricesUpdatedAt));
        assertThat(result.commodity(), is(mockCommodityDto));
        assertThat(result.station(), is(mockStationDto));
        assertThat(result.supply(), is(100L));
        assertThat(result.demand(), is(200L));
        assertThat(result.buyPrice(), is(1234L));
        assertThat(result.sellPrice(), is(4321L));
        assertThat(result.distance(), is(63.5));
        
        verify(commodityDtoMapper, times(1)).map(domainObject.getValidatedCommodity());
        verify(stationDtoMapper, times(1)).map(domainObject.getStation());
    }
    
}