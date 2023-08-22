package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisPersistenceLocateCommodityFilterMapperTest {
    
    private MybatisPersistenceLocateCommodityFilterMapper underTest;

    @BeforeEach
    public void setUp(){
        underTest = new MybatisPersistenceLocateCommodityFilterMapper();
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        
        LocateCommodityFilter domainObject = LocateCommodityFilter.builder()
                .commodityDisplayName("Display Name")
                .xCoordinate(1.0)
                .yCoordinate(2.0)
                .zCoordinate(3.0)
                .includePlanetary(true)
                .includeOdyssey(false)
                .includeFleetCarriers(true)
                .maxLandingPadSize(LandingPadSize.MEDIUM)
                .minSupply(123L)
                .minDemand(321L)
                .build();
        
        MybatisLocateCommodityFilter entity = underTest.map(domainObject);
        
        assertThat(entity.getCommodityDisplayName(), is("Display Name"));
        assertThat(entity.getXCoordinate(), is(1.0));
        assertThat(entity.getYCoordinate(), is(2.0));
        assertThat(entity.getZCoordinate(), is(3.0));
        assertThat(entity.getIncludeFleetCarriers(), is(true));
        assertThat(entity.getIncludeOdyssey(), is(false));
        assertThat(entity.getIncludeFleetCarriers(), is(true));
        assertThat(entity.getMaxLandingPadSize(), is(String.valueOf(LandingPadSize.MEDIUM)));
        assertThat(entity.getMinSupply(), is(123L));
        assertThat(entity.getMinDemand(), is(321L));
        
    }
}