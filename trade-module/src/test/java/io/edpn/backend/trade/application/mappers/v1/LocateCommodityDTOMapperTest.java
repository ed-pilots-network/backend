package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.CoordinateDTO;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

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
        Commodity commodity = new Commodity();
        commodity.setName("Gold");

        Station station = new Station();
        station.setName("Station 1");
        station.setArrivalDistance(20.0);
        station.setMaxLandingPadSize(LandingPadSize.LARGE);
        station.setPlanetary(false);
        station.setRequireOdyssey(false);
        station.setFleetCarrier(false);

        System system = new System();
        system.setName("System 1");
        system.setXCoordinate(1.0);
        system.setYCoordinate(2.0);
        system.setZCoordinate(3.0);

        LocateCommodity locateCommodity = new LocateCommodity();
        locateCommodity.setPricesUpdatedAt(LocalDateTime.now());
        locateCommodity.setCommodity(commodity);
        locateCommodity.setStation(station);
        locateCommodity.setSystem(system);
        locateCommodity.setSupply(1000L);
        locateCommodity.setDemand(2000L);
        locateCommodity.setBuyPrice(100L);
        locateCommodity.setSellPrice(150L);

        // When
        LocateCommodityResponse response = mapper.map(locateCommodity);

        // Then
        assertThat(response, is(notNullValue()));
        assertThat(response.getPricesUpdatedAt(), is(equalTo(locateCommodity.getPricesUpdatedAt())));
        assertThat(response.getCommodityName(), is(equalTo(commodity.getName())));
        assertThat(response.getStation(), is(notNullValue()));
        assertThat(response.getStation().getName(), is(equalTo(station.getName())));
        assertThat(response.getStation().getArrivalDistance(), is(equalTo(station.getArrivalDistance())));
        assertThat(response.getStation().getMaxLandingPadSize(), is(equalTo(station.getMaxLandingPadSize().name())));
        assertThat(response.getStation().getPlanetary(), is(equalTo(station.getPlanetary())));
        assertThat(response.getStation().getRequireOdyssey(), is(equalTo(station.getRequireOdyssey())));
        assertThat(response.getStation().getFleetCarrier(), is(equalTo(station.getFleetCarrier())));
        assertThat(response.getSystem(), is(notNullValue()));
        assertThat(response.getSystem().getName(), is(equalTo(system.getName())));
        assertThat(response.getSystem().getCoordinateDTO(), is(notNullValue()));
        assertThat(response.getSystem().getCoordinateDTO().getXCoordinate(), is(equalTo(system.getXCoordinate())));
        assertThat(response.getSystem().getCoordinateDTO().getYCoordinate(), is(equalTo(system.getYCoordinate())));
        assertThat(response.getSystem().getCoordinateDTO().getZCoordinate(), is(equalTo(system.getZCoordinate())));
        assertThat(response.getSupply(), is(equalTo(locateCommodity.getSupply())));
        assertThat(response.getDemand(), is(equalTo(locateCommodity.getDemand())));
        assertThat(response.getBuyPrice(), is(equalTo(locateCommodity.getBuyPrice())));
        assertThat(response.getSellPrice(), is(equalTo(locateCommodity.getSellPrice())));
    }

    @Test
    void shouldMapLocateCommodityRequestToFilter() {
        // Given
        UUID commodityId = UUID.randomUUID();
        LocateCommodityRequest request = LocateCommodityRequest.builder()
                .commodityId(commodityId)
                .referenceLocation(new CoordinateDTO(1.0, 2.0, 3.0))
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
        assertThat(filter.getCommodityId(), is(equalTo(request.getCommodityId())));
        assertThat(filter.getXCoordinate(), is(equalTo(request.getReferenceLocation().getXCoordinate())));
        assertThat(filter.getYCoordinate(), is(equalTo(request.getReferenceLocation().getYCoordinate())));
        assertThat(filter.getZCoordinate(), is(equalTo(request.getReferenceLocation().getZCoordinate())));
        assertThat(filter.getIncludePlanetary(), is(equalTo(request.getIncludePlanetary())));
        assertThat(filter.getIncludeOdyssey(), is(equalTo(request.getIncludeOdyssey())));
        assertThat(filter.getIncludeFleetCarriers(), is(equalTo(request.getIncludeFleetCarriers())));
        assertThat(filter.getMaxLandingPadSize(), is(equalTo(LandingPadSize.valueOf(request.getMaxLandingPadSize()))));
        assertThat(filter.getMinSupply(), is(equalTo(request.getMinSupply())));
        assertThat(filter.getMinDemand(), is(equalTo(request.getMinDemand())));
    }
}