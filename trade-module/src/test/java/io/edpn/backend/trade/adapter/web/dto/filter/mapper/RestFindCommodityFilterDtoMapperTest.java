package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.RestFindCommodityFilterDto;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RestFindCommodityFilterDtoMapperTest {
    
    private FindCommodityFilterDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestFindCommodityFilterDtoMapper();
    }
    
    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        FindCommodityFilterDto dto = new RestFindCommodityFilterDto(
                "type",
                true);
        
        FindCommodityFilter domainObject = underTest.map(dto);
        
        assertThat(domainObject.getType(), is("type"));
        assertThat(domainObject.getIsRare(), is(true));
    }

}