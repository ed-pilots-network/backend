package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityType;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ValidatedCommodityEntityMapperTest {

    private ValidatedCommodityEntityMapper underTest;

    @BeforeEach
    void setup() {
        underTest = new ValidatedCommodityEntityMapper();
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        UUID id = UUID.randomUUID();

        ValidatedCommodityEntity entity = ValidatedCommodityEntity.builder()
                .id(id)
                .commodityName("commodityName")
                .displayName("Commodity Name")
                .type("TECHNOLOGY")
                .isRare(true)
                .build();

        ValidatedCommodity domainObject = underTest.map(entity);

        assertThat(domainObject, is(notNullValue()));
        assertThat(domainObject.id(), is(id));
        assertThat(domainObject.commodityName(), is("commodityName"));
        assertThat(domainObject.displayName(), is("Commodity Name"));
        assertThat(domainObject.type(), is(CommodityType.TECHNOLOGY));
        assertThat(domainObject.isRare(), is(true));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        UUID id = UUID.randomUUID();

        ValidatedCommodity domainObject = new ValidatedCommodity(
                id,
                "commodityName",
                "Commodity Name",
                CommodityType.TECHNOLOGY,
                true
        );

        ValidatedCommodityEntity entity = underTest.map(domainObject);

        assertThat(entity, is(notNullValue()));
        assertThat(entity.getId(), is(id));
        assertThat(entity.getCommodityName(), is("commodityName"));
        assertThat(entity.getDisplayName(), is("Commodity Name"));
        assertThat(entity.getType(), is("TECHNOLOGY"));
        assertThat(entity.getIsRare(), is(true));
    }
}