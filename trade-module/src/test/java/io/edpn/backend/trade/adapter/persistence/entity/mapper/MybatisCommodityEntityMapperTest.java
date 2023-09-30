package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MybatisCommodityEntityMapperTest {
    
    private CommodityEntityMapper<MybatisCommodityEntity> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisCommodityEntityMapper();
    }
    
    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        
        UUID uuid = UUID.randomUUID();
        
        CommodityEntity entity = MybatisCommodityEntity.builder()
                .id(uuid)
                .name("commodityName")
                .build();
        
        Commodity result = underTest.map(entity);
        
        assertThat(result.getId(), equalTo(entity.getId()));
        assertThat(result.getName(), equalTo(entity.getName()));
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        
        UUID uuid = UUID.randomUUID();
        
        Commodity domainObject = Commodity.builder()
                .id(uuid)
                .name("commodityName")
                .build();
        
        CommodityEntity result = underTest.map(domainObject);
        
        assertThat(result.getId(), equalTo(domainObject.getId()));
        assertThat(result.getName(), equalTo(domainObject.getName()));
    }
    
}