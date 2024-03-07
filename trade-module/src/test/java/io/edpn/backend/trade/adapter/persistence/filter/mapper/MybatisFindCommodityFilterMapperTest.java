package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindCommodityFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisFindCommodityFilterMapperTest {
    
    private MybatisFindCommodityFilterMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new MybatisFindCommodityFilterMapper();
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        
        io.edpn.backend.trade.application.domain.filter.FindCommodityFilter domainObject = io.edpn.backend.trade.application.domain.filter.FindCommodityFilter.builder()
                .type("type")
                .isRare(true)
                .build();
        
        MybatisFindCommodityFilter entity = underTest.map(domainObject);
        
        assertThat(entity.getType(), is("type"));
        assertThat(entity.getIsRare(), is(true));
    }
}