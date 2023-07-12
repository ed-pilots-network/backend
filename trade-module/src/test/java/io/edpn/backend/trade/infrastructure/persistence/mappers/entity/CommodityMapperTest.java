package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class CommodityMapperTest {

    private CommodityMapper underTest;

    @BeforeEach
    public void setup() {
        underTest = new CommodityMapper();
    }

    @Test
    public void shouldMapCommodityEntityToCommodity() {
        UUID id = UUID.randomUUID();
        String name = "Commodity name";

        CommodityEntity commodityEntity = CommodityEntity.builder()
                .id(id)
                .name(name)
                .build();

        Commodity commodity = underTest.map(commodityEntity);

        assertThat(commodity.getId(), is(id));
        assertThat(commodity.getName(), is(name));
    }

    @Test
    public void shouldMapCommodityToCommodityEntity() {
        UUID id = UUID.randomUUID();
        String name = "Commodity name";

        Commodity commodity = Commodity.builder()
                .id(id)
                .name(name)
                .build();

        CommodityEntity commodityEntity = underTest.map(commodity);

        assertThat(commodityEntity.getId(), is(id));
        assertThat(commodityEntity.getName(), is(name));
    }
}
