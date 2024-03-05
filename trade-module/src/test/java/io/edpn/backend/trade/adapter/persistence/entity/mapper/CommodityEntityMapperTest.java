package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.application.domain.Commodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class CommodityEntityMapperTest {

    private CommodityEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new CommodityEntityMapper();
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {

        UUID uuid = UUID.randomUUID();

        CommodityEntity entity = CommodityEntity.builder()
                .id(uuid)
                .name("commodityName")
                .build();

        Commodity result = underTest.map(entity);

        assertThat(result.id(), equalTo(entity.getId()));
        assertThat(result.name(), equalTo(entity.getName()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        UUID uuid = UUID.randomUUID();

        Commodity domainObject = new Commodity(uuid, "commodityName");

        CommodityEntity result = underTest.map(domainObject);

        assertThat(result.getId(), equalTo(domainObject.id()));
        assertThat(result.getName(), equalTo(domainObject.name()));
    }

}