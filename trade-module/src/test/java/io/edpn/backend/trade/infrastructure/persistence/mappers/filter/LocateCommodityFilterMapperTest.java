package io.edpn.backend.trade.infrastructure.persistence.mappers.filter;

import io.edpn.backend.trade.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.infrastructure.persistence.filter.LocateCommodityFilterPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class LocateCommodityFilterMapperTest {

    private LocateCommodityFilterMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityFilterMapper();
    }

    @Test
    public void shouldMapLocateCommodityFilterToPersistence() {
        UUID commodityId = UUID.randomUUID();
        Double xCoordinate = 100.0;
        Double yCoordinate = 200.0;
        Double zCoordinate = 300.0;
        Boolean includePlanetary = true;
        Boolean includeOdyssey = false;
        Boolean includeFleetCarriers = true;
        LandingPadSize maxLandingPadSize = LandingPadSize.LARGE;
        Long minSupply = 10L;
        Long minDemand = 20L;

        LocateCommodityFilter filter = LocateCommodityFilter.builder()
                .commodityId(commodityId)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .includePlanetary(includePlanetary)
                .includeOdyssey(includeOdyssey)
                .includeFleetCarriers(includeFleetCarriers)
                .maxLandingPadSize(maxLandingPadSize)
                .minSupply(minSupply)
                .minDemand(minDemand)
                .build();

        LocateCommodityFilterPersistence persistence = underTest.map(filter);

        assertThat(persistence.getCommodityId(), is(commodityId));
        assertThat(persistence.getXCoordinate(), is(xCoordinate));
        assertThat(persistence.getYCoordinate(), is(yCoordinate));
        assertThat(persistence.getZCoordinate(), is(zCoordinate));
        assertThat(persistence.getIncludePlanetary(), is(includePlanetary));
        assertThat(persistence.getIncludeOdyssey(), is(includeOdyssey));
        assertThat(persistence.getIncludeFleetCarriers(), is(includeFleetCarriers));
        assertThat(persistence.getMaxLandingPadSize(), is(maxLandingPadSize.name()));
        assertThat(persistence.getMinSupply(), is(minSupply));
        assertThat(persistence.getMinDemand(), is(minDemand));
    }
}
