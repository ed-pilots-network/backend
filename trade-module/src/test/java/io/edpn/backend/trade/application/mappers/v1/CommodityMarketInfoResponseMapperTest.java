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

class CommodityMarketInfoResponseMapperTest {

    private CommodityMarketInfoResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CommodityMarketInfoResponseMapper();
    }

    @Test
    void shouldMapCommodityMarketInfoToResponse() {
        // Given
        Commodity commodity = new Commodity();
        commodity.setName("Gold");

        Station station = new Station();
        station.setName("Station 1");
        station.setArrivalDistance(20.0);

        System system = new System();
        system.setName("System 1");
        system.setXCoordinate(1.0);
        system.setYCoordinate(2.0);
        system.setZCoordinate(3.0);

        station.setSystem(system);

        CommodityMarketInfo commodityMarketInfo = new CommodityMarketInfo();
        commodityMarketInfo.setCommodity(commodity);
        commodityMarketInfo.setMaxBuyPrice(100.0);
        commodityMarketInfo.setMinBuyPrice(50.0);
        commodityMarketInfo.setAvgBuyPrice(75.0);
        commodityMarketInfo.setMaxSellPrice(150.0);
        commodityMarketInfo.setMinSellPrice(100.0);
        commodityMarketInfo.setAvgSellPrice(125.0);
        commodityMarketInfo.setMinMeanPrice(60.0);
        commodityMarketInfo.setMaxMeanPrice(140.0);
        commodityMarketInfo.setAverageMeanPrice(100.0);
        commodityMarketInfo.setTotalStock(1000L);
        commodityMarketInfo.setTotalDemand(2000L);
        commodityMarketInfo.setTotalStations(10);
        commodityMarketInfo.setStationsWithBuyPrice(5);
        commodityMarketInfo.setStationsWithSellPrice(6);
        commodityMarketInfo.setStationsWithBuyPriceLowerThanAverage(2);
        commodityMarketInfo.setStationsWithSellPriceHigherThanAverage(3);
        commodityMarketInfo.setHighestSellingToStation(station);
        commodityMarketInfo.setLowestBuyingFromStation(station);

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
        assertThat(response.getHighestSellingToStation().getName(), is(equalTo(station.getName())));
        assertThat(response.getHighestSellingToStation().getSystem(), is(notNullValue()));
        assertThat(response.getHighestSellingToStation().getSystem().getName(), is(equalTo(system.getName())));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates(), is(notNullValue()));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates().getXCoordinate(), is(equalTo(system.getXCoordinate())));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates().getYCoordinate(), is(equalTo(system.getYCoordinate())));
        assertThat(response.getHighestSellingToStation().getSystem().getCoordinates().getZCoordinate(), is(equalTo(system.getZCoordinate())));
        assertThat(response.getLowestBuyingFromStation(), is(notNullValue()));
        assertThat(response.getLowestBuyingFromStation().getName(), is(equalTo(station.getName())));
        assertThat(response.getLowestBuyingFromStation().getSystem(), is(notNullValue()));
        assertThat(response.getLowestBuyingFromStation().getSystem().getName(), is(equalTo(system.getName())));
        assertThat(response.getLowestBuyingFromStation().getSystem().getCoordinates(), is(notNullValue()));
        assertThat(response.getLowestBuyingFromStation().getSystem().getCoordinates().getXCoordinate(), is(equalTo(system.getXCoordinate())));
        assertThat(response.getLowestBuyingFromStation().getSystem().getCoordinates().getYCoordinate(), is(equalTo(system.getYCoordinate())));
        assertThat(response.getLowestBuyingFromStation().getSystem().getCoordinates().getZCoordinate(), is(equalTo(system.getZCoordinate())));
    }
}
