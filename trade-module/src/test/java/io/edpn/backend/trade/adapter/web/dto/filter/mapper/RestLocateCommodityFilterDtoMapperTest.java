package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.RestLocateCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.filter.RestPageFilterDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.PageFilterDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestLocateCommodityFilterDtoMapperTest {

    private LocateCommodityFilterDtoMapper underTest;

    @Mock
    private PageFilterDtoMapper pageFilterDtoMapper;

    @BeforeEach
    public void setUp() {
        underTest = new RestLocateCommodityFilterDtoMapper(pageFilterDtoMapper);
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject_nullPageFilter() {
        RestPageFilterDto pageFilterDto = null;
        LocateCommodityFilterDto dto = new RestLocateCommodityFilterDto(
                "Display Name",
                1.0,
                2.0,
                3.0,
                true,
                false,
                true,
                String.valueOf(LandingPadSize.MEDIUM),
                123L,
                321L,
                pageFilterDto);
        when(pageFilterDtoMapper.getDefaultFilter()).thenReturn(PageFilter.builder()
                .size(20)
                .page(0)
                .build());

        LocateCommodityFilter domainObject = underTest.map(dto);

        assertThat(domainObject.getCommodityDisplayName(), is("Display Name"));
        assertThat(domainObject.getXCoordinate(), is(1.0));
        assertThat(domainObject.getYCoordinate(), is(2.0));
        assertThat(domainObject.getZCoordinate(), is(3.0));
        assertThat(domainObject.getIncludePlanetary(), is(true));
        assertThat(domainObject.getIncludeOdyssey(), is(false));
        assertThat(domainObject.getIncludeFleetCarriers(), is(true));
        assertThat(domainObject.getShipSize(), is(LandingPadSize.MEDIUM));
        assertThat(domainObject.getMinSupply(), is(123L));
        assertThat(domainObject.getMinDemand(), is(321L));
        assertThat(domainObject.getPageFilter().getPage(), is(0));
        assertThat(domainObject.getPageFilter().getSize(), is(20));
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        RestPageFilterDto pageFilterDto = new RestPageFilterDto(20, 4);
        LocateCommodityFilterDto dto = new RestLocateCommodityFilterDto(
                "Display Name",
                1.0,
                2.0,
                3.0,
                true,
                false,
                true,
                String.valueOf(LandingPadSize.MEDIUM),
                123L,
                321L,
                pageFilterDto);
        when(pageFilterDtoMapper.map(pageFilterDto)).thenReturn(PageFilter.builder()
                .size(20)
                .page(4)
                .build());

        LocateCommodityFilter domainObject = underTest.map(dto);

        assertThat(domainObject.getCommodityDisplayName(), is("Display Name"));
        assertThat(domainObject.getXCoordinate(), is(1.0));
        assertThat(domainObject.getYCoordinate(), is(2.0));
        assertThat(domainObject.getZCoordinate(), is(3.0));
        assertThat(domainObject.getIncludePlanetary(), is(true));
        assertThat(domainObject.getIncludeOdyssey(), is(false));
        assertThat(domainObject.getIncludeFleetCarriers(), is(true));
        assertThat(domainObject.getShipSize(), is(LandingPadSize.MEDIUM));
        assertThat(domainObject.getMinSupply(), is(123L));
        assertThat(domainObject.getMinDemand(), is(321L));
        assertThat(domainObject.getPageFilter().getPage(), is(4));
        assertThat(domainObject.getPageFilter().getSize(), is(20));
    }
}