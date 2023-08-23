package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityType;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class MybatisValidatedCommodityEntityMapperTest {
    
    private ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> underTest;
    
    @BeforeEach
    void setup() {
        underTest = new MybatisValidatedCommodityEntityMapper();
    }
    
    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        UUID id = UUID.randomUUID();
        
        MybatisValidatedCommodityEntity entity = MybatisValidatedCommodityEntity.builder()
                .id(id)
                .commodityName("commodityName")
                .displayName("Commodity Name")
                .type("TECHNOLOGY")
                .isRare(true)
                .build();
        
        ValidatedCommodity domainObject = underTest.map(entity);
        
        assertThat(domainObject, is(notNullValue()));
        assertThat(domainObject.getId(), is(id));
        assertThat(domainObject.getCommodityName(), is("commodityName"));
        assertThat(domainObject.getDisplayName(), is("Commodity Name"));
        assertThat(domainObject.getType(), is(CommodityType.TECHNOLOGY));
        assertThat(domainObject.getIsRare(), is(true));
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        
        ValidatedCommodity domainObject = ValidatedCommodity.builder()
                .id(id)
                .commodityName("commodityName")
                .displayName("Commodity Name")
                .type(CommodityType.TECHNOLOGY)
                .isRare(true)
                .build();
        
        MybatisValidatedCommodityEntity entity = underTest.map(domainObject);
        
        assertThat(entity, is(notNullValue()));
        assertThat(entity.getId(), is(id));
        assertThat(entity.getCommodityName(), is("commodityName"));
        assertThat(entity.getDisplayName(), is("Commodity Name"));
        assertThat(entity.getType(), is("TECHNOLOGY"));
        assertThat(entity.getIsRare(), is(true));
    }
}