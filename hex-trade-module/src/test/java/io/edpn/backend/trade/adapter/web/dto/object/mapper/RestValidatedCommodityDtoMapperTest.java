package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.application.domain.CommodityType;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class RestValidatedCommodityDtoMapperTest {
    
    private ValidatedCommodityDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestValidatedCommodityDtoMapper();
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnDto() {
        
        ValidatedCommodity domainObject = ValidatedCommodity.builder()
                .commodityName("commodityName")
                .displayName("Commodity Name")
                .type(CommodityType.TECHNOLOGY)
                .isRare(true)
                .build();
        
        ValidatedCommodityDto entity = underTest.map(domainObject);
        
        assertThat(entity, is(notNullValue()));
        assertThat(entity.commodityName(), is("commodityName"));
        assertThat(entity.displayName(), is("Commodity Name"));
        assertThat(entity.type(), is("TECHNOLOGY"));
        assertThat(entity.isRare(), is(true));
    }

}