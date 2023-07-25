package io.edpn.backend.trade.infrastructure.persistence.mappers.filter;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.CommodityType;
import io.edpn.backend.trade.infrastructure.persistence.filter.FindCommodityFilterPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class FindCommodityFilterMapperTest {
    
    private FindCommodityFilterMapper underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new FindCommodityFilterMapper();
    }
    
    @Test
    void shouldMapFindCommodityFilterToPersistence(){
        String type = "TECHNOLOGY";
        Boolean isRare = true;
        
        FindCommodityFilter filter = FindCommodityFilter.builder()
                .type(CommodityType.valueOf(type))
                .isRare(isRare)
                .build();
        
        FindCommodityFilterPersistence response = underTest.map(filter);
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getType(), is(type));
        assertThat(response.getIsRare(), is(isRare));
    }

}