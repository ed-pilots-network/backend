package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.RestLocateCommodityFilterDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class RestLocateCommodityFilterDtoMapperTest {
    
    private LocateCommodityFilterDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestLocateCommodityFilterDtoMapper();
    }
    
    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
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
                321L);
        
        LocateCommodityFilter domainObject = underTest.map(dto);
        
        assertThat(domainObject.getCommodityDisplayName(), is("Display Name"));
        assertThat(domainObject.getXCoordinate(), is(1.0));
        assertThat(domainObject.getYCoordinate(), is(2.0));
        assertThat(domainObject.getZCoordinate(), is(3.0));
        assertThat(domainObject.getIncludePlanetary(), is(true));
        assertThat(domainObject.getIncludeOdyssey(), is(false));
        assertThat(domainObject.getIncludeFleetCarriers(), is(true));
        assertThat(domainObject.getMaxLandingPadSize(), is(LandingPadSize.MEDIUM));
        assertThat(domainObject.getMinSupply(), is(123L));
        assertThat(domainObject.getMinDemand(), is(321L));
    }
}