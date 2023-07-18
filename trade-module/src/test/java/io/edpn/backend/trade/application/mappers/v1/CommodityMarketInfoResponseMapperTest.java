package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class CommodityMarketInfoResponseMapperTest {

    private CommodityMarketInfoResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CommodityMarketInfoResponseMapper();
    }

    @Test
    void shouldMapCommodityMarketInfoToResponse() {
        // Given
        Commodity commodity = Commodity.builder()
                .name("Gold")
                .build();

        Station station1 = Station.builder()
                .name("Station 1")
                .arrivalDistance(20.0)
                .build();

        Station station2 = Station.builder()
                .name("Station 2")
                .arrivalDistance(46.0)
                .build();

        System system1 = System.builder()
                .name("System 1")
                .xCoordinate(1.0)
                .yCoordinate(2.0)
                .zCoordinate(3.0)
                .build();

        System system2 = System.builder()
                .name("System 2")
                .xCoordinate(null)
                .yCoordinate(null)
                .zCoordinate(null)
                .build();

        station1.setSystem(system1);
        station2.setSystem(system2);

        CommodityMarketInfo commodityMarketInfo = CommodityMarketInfo.builder()
                .commodity(commodity)
                .maxBuyPrice(100.0)
                .minBuyPrice(50.0)
                .avgBuyPrice(75.0)
                .maxSellPrice(150.0)
                .minSellPrice(100.0)
                .avgSellPrice(125.0)
                .minMeanPrice(60.0)
                .maxMeanPrice(140.0)
                .averageMeanPrice(100.0)
                .totalStock(1000L)
                .totalDemand(2000L)
                .totalStations(10)
                .stationsWithBuyPrice(5)
                .stationsWithSellPrice(6)
                .stationsWithBuyPriceLowerThanAverage(2)
                .stationsWithSellPriceHigherThanAverage(3)
                .highestSellingToStation(station1)
                .lowestBuyingFromStation(station2)
                .build();

        // When
        CommodityMarketInfoResponse response = mapper.map(commodityMarketInfo);

        // Then
        assertThat(response, is(notNullValue()));
        assertThat(response.getCommodityName(), is(equalTo(commodity.getName())));
        assertThat(response.getMaxBuyPrice(), is(equalTo(commodityMarketInfo.getMaxBuyPrice())));
        assertThat(response.getMinBuyPrice(), is(equalTo(commodityMarketInfo.getMinBuyPrice())));
        assertThat(response.getAvgBuyPrice(), is(equalTo(commodityMarketInfo.getAvgBuyPrice())));
        assertThat(response.getMaxSellPrice(), is(equalTo(commodityMarketInfo.getMaxSellPrice())));
        assertThat(response.getMinSellPrice(), is(equalTo(commodityMarketInfo.getMinSellPrice())));
        assertThat(response.getAvgSellPrice(), is(equalTo(commodityMarketInfo.getAvgSellPrice())));
        assertThat(response.getMinMeanPrice(), is(equalTo(commodityMarketInfo.getMinMeanPrice())));
        assertThat(response.getMaxMeanPrice(), is(equalTo(commodityMarketInfo.getMaxMeanPrice())));
        assertThat(response.getAverageMeanPrice(), is(equalTo(commodityMarketInfo.getAverageMeanPrice())));
        assertThat(response.getTotalStock(), is(equalTo(commodityMarketInfo.getTotalStock())));
        assertThat(response.getTotalDemand(), is(equalTo(commodityMarketInfo.getTotalDemand())));
        assertThat(response.getTotalStations(), is(equalTo(commodityMarketInfo.getTotalStations())));
        assertThat(response.getStationsWithBuyPrice(), is(equalTo(commodityMarketInfo.getStationsWithBuyPrice())));
        assertThat(response.getStationsWithSellPrice(), is(equalTo(commodityMarketInfo.getStationsWithSellPrice())));
        assertThat(response.getStationsWithBuyPriceLowerThanAverage(), is(equalTo(commodityMarketInfo.getStationsWithBuyPriceLowerThanAverage())));
        assertThat(response.getStationsWithSellPriceHigherThanAverage(), is(equalTo(commodityMarketInfo.getStationsWithSellPriceHigherThanAverage())));
        assertThat(response.getHighestSellingToStation(), is(notNullValue()));
        assertThat(response.getHighestSellingToStation().getName(), is(equalTo(station1.getName())));
        assertThat(response.getHighestSellingToStation().getSystem(), is(notNullValue()));
        assertThat(response.getHighestSellingToStation().getSystem().getName(), is(equalTo(system1.getName())));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates(), is(notNullValue()));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates().getX(), is(equalTo(system1.getXCoordinate())));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates().getY(), is(equalTo(system1.getYCoordinate())));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates().getZ(), is(equalTo(system1.getZCoordinate())));
        assertThat(response.getLowestBuyingFromStation(), is(notNullValue()));
        assertThat(response.getLowestBuyingFromStation().getName(), is(equalTo(station2.getName())));
        assertThat(response.getLowestBuyingFromStation().getSystem(), is(notNullValue()));
        assertThat(response.getLowestBuyingFromStation().getSystem().getName(), is(equalTo(system2.getName())));
        assertThat(response.getLowestBuyingFromStation().getSystem().getCoordinates(), is(nullValue()));
    }
}
