package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.CommodityType;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class ValidatedCommodityMapperTest {
    
    private ValidatedCommodityMapper underTest;
    
    @BeforeEach
    void setup() {
        underTest = new ValidatedCommodityMapper();
    }
    
    @Test
    void shouldMapValidatedCommodityEntityToValidatedCommodity() {
        UUID id = UUID.randomUUID();
        
        ValidatedCommodityEntity validatedCommodityEntity = ValidatedCommodityEntity.builder()
                .id(id)
                .commodityName("commodityname")
                .displayName("Commodity Name")
                .type("TECHNOLOGY")
                .isRare(true)
                .build();
        
        
        
        ValidatedCommodity response = underTest.map(validatedCommodityEntity);
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getCommodityName(), is(validatedCommodityEntity.getCommodityName()));
        assertThat(response.getDisplayName(), is(validatedCommodityEntity.getDisplayName()));
        assertThat(response.getType(), is(CommodityType.valueOf(validatedCommodityEntity.getType())));
        assertThat(response.getIsRare(), is(validatedCommodityEntity.getIsRare()));
    }
}