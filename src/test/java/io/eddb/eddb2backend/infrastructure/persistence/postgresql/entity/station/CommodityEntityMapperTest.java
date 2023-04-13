package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Commodity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CommodityEntityMapperTest {
    private Commodity commodity;
    private CommodityEntity commodityEntity;

    @BeforeEach
    public void setUp() {
        commodity = Commodity.builder()
                .id(1L)
                .name("Test Commodity")
                .build();

        commodityEntity = CommodityEntity.builder()
                .id(1L)
                .name("Test Commodity")
                .build();
    }

    @Test
    public void map_commodityToCommodityEntity() {
        Optional<CommodityEntity> mappedEntity = CommodityEntity.Mapper.map(commodity);
        assertThat(mappedEntity.isPresent(), is(true));
        assertThat(mappedEntity.get(), is(commodityEntity));
    }

    @Test
    public void map_commodityEntityToCommodity() {
        Optional<Commodity> mappedCommodity = CommodityEntity.Mapper.map(commodityEntity);
        assertThat(mappedCommodity.isPresent(), is(true));
        assertThat(mappedCommodity.get(), is(commodity));
    }
}
