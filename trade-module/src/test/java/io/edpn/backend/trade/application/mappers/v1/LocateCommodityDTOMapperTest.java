package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.CommodityType;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import java.time.LocalDateTime;

import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LocateCommodityDTOMapperTest {

    private LocateCommodityDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new LocateCommodityDTOMapper();
    }

    @Test
    void shouldMapLocateCommodityToResponse() {
        // Given
        ValidatedCommodity validatedCommodity = ValidatedCommodity.builder()
                .commodityName("somethingObscure")
                .displayName("Tritium")
                .type(CommodityType.METALS)
                .isRare(true)
                .build();

        Station station = Station.builder()
                .name("Station 1")
                .arrivalDistance(20.0)
                .maxLandingPadSize(LandingPadSize.LARGE)
                .planetary(false)
                .requireOdyssey(false)
                .fleetCarrier(false)
                .build();

        System system = System.builder()
                .name("System 1")
                .xCoordinate(1.0)
                .yCoordinate(2.0)
                .zCoordinate(3.0)
                .build();

        LocateCommodity locateCommodity = LocateCommodity.builder()
                .pricesUpdatedAt(LocalDateTime.now())
                .validatedCommodity(validatedCommodity)
                .station(station)
                .system(system)
                .supply(1000L)
                .demand(2000L)
                .buyPrice(100L)
                .sellPrice(150L)
                .distance(80.0)
                .build();

        // When
        LocateCommodityResponse response = mapper.map(locateCommodity);

        // Then
        assertThat(response, is(notNullValue()));
        assertThat(response.getPricesUpdatedAt(), is(equalTo(locateCommodity.getPricesUpdatedAt())));
        assertThat(response.getCommodityDisplayName(), is(equalTo(validatedCommodity.getDisplayName())));
        assertThat(response.getStation(), is(notNullValue()));
        assertThat(response.getStation().getName(), is(equalTo(station.getName())));
        assertThat(response.getStation().getArrivalDistance(), is(equalTo(station.getArrivalDistance())));
        assertThat(response.getStation().getMaxLandingPadSize(), is(equalTo(station.getMaxLandingPadSize().name())));
        assertThat(response.getStation().getPlanetary(), is(equalTo(station.getPlanetary())));
        assertThat(response.getStation().getRequireOdyssey(), is(equalTo(station.getRequireOdyssey())));
        assertThat(response.getStation().getFleetCarrier(), is(equalTo(station.getFleetCarrier())));
        assertThat(response.getSystemName(), is(equalTo(system.getName())));
        assertThat(response.getSupply(), is(equalTo(locateCommodity.getSupply())));
        assertThat(response.getDemand(), is(equalTo(locateCommodity.getDemand())));
        assertThat(response.getBuyPrice(), is(equalTo(locateCommodity.getBuyPrice())));
        assertThat(response.getSellPrice(), is(equalTo(locateCommodity.getSellPrice())));
        assertThat(response.getDistance(), is(equalTo(locateCommodity.getDistance())));
    }

    @Test
    void shouldMapLocateCommodityRequestToFilter() {
        // Given
        String displayName = "Commodity Name";
        LocateCommodityRequest request = LocateCommodityRequest.builder()
                .commodityDisplayName(displayName)
                .x(1.0)
                .y(2.0)
                .z(3.0)
                .includePlanetary(false)
                .includeOdyssey(false)
                .includeFleetCarriers(false)
                .maxLandingPadSize("LARGE")
                .minSupply(1000L)
                .minDemand(2000L)
                .build();

        // When
        LocateCommodityFilter filter = mapper.map(request);

        // Then
        assertThat(filter, is(notNullValue()));
        assertThat(filter.getCommodityDisplayName(), is(equalTo(request.getCommodityDisplayName())));
        assertThat(filter.getXCoordinate(), is(equalTo(request.getX())));
        assertThat(filter.getYCoordinate(), is(equalTo(request.getY())));
        assertThat(filter.getZCoordinate(), is(equalTo(request.getZ())));
        assertThat(filter.getIncludePlanetary(), is(equalTo(request.getIncludePlanetary())));
        assertThat(filter.getIncludeOdyssey(), is(equalTo(request.getIncludeOdyssey())));
        assertThat(filter.getIncludeFleetCarriers(), is(equalTo(request.getIncludeFleetCarriers())));
        assertThat(filter.getMaxLandingPadSize(), is(equalTo(LandingPadSize.valueOf(request.getMaxLandingPadSize()))));
        assertThat(filter.getMinSupply(), is(equalTo(request.getMinSupply())));
        assertThat(filter.getMinDemand(), is(equalTo(request.getMinDemand())));
    }
}
