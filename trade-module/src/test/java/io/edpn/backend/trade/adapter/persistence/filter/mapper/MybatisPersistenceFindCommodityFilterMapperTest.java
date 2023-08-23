package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisPersistenceFindCommodityFilterMapperTest {
    
    private MybatisPersistenceFindCommodityFilterMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new MybatisPersistenceFindCommodityFilterMapper();
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        
        FindCommodityFilter domainObject = FindCommodityFilter.builder()
                .type("type")
                .isRare(true)
                .build();
        
        MybatisFindCommodityFilter entity = underTest.map(domainObject);
        
        assertThat(entity.getType(), is("type"));
        assertThat(entity.getIsRare(), is(true));
    }
}