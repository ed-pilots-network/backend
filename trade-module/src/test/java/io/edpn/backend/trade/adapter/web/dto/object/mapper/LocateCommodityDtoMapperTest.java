package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.LocateCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.StationDto;
import io.edpn.backend.trade.adapter.web.dto.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
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
class LocateCommodityDtoMapperTest {

    @Mock
    private StationDtoMapper stationDtoMapper;

    @Mock
    private ValidatedCommodityDtoMapper commodityDtoMapper;

    private LocateCommodityDtoMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityDtoMapper(stationDtoMapper, commodityDtoMapper);
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnDto() {
        ValidatedCommodityDto mockCommodityDto = mock(ValidatedCommodityDto.class);
        StationDto mockStationDto = mock(StationDto.class);
        LocalDateTime pricesUpdatedAt = LocalDateTime.now();

        LocateCommodity domainObject = new LocateCommodity(
                pricesUpdatedAt,
                mock(ValidatedCommodity.class),
                mock(Station.class),
                100L,
                200L,
                1234L,
                4321L,
                63.5
        );

        when(commodityDtoMapper.map(domainObject.validatedCommodity())).thenReturn(mockCommodityDto);
        when(stationDtoMapper.map(domainObject.station())).thenReturn(mockStationDto);

        LocateCommodityDto result = underTest.map(domainObject);

        assertThat(result.priceUpdatedAt(), is(pricesUpdatedAt));
        assertThat(result.commodity(), is(mockCommodityDto));
        assertThat(result.station(), is(mockStationDto));
        assertThat(result.supply(), is(100L));
        assertThat(result.demand(), is(200L));
        assertThat(result.buyPrice(), is(1234L));
        assertThat(result.sellPrice(), is(4321L));
        assertThat(result.distance(), is(63.5));

        verify(commodityDtoMapper, times(1)).map(domainObject.validatedCommodity());
        verify(stationDtoMapper, times(1)).map(domainObject.station());
    }

}