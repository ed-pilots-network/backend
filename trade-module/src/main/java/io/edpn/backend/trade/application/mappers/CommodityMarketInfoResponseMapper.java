package io.edpn.backend.trade.application.mappers;

import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.model.Station;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommodityMarketInfoResponseMapper {

    public CommodityMarketInfoResponse map(CommodityMarketInfo commoditymarketInfo) {
        return CommodityMarketInfoResponse.builder()
                .commodityName(commoditymarketInfo.getCommodity().getName())
                .maxBuyPrice(commoditymarketInfo.getMaxBuyPrice())
                .minBuyPrice(commoditymarketInfo.getMinBuyPrice())
                .avgBuyPrice(commoditymarketInfo.getAvgBuyPrice())
                .maxSellPrice(commoditymarketInfo.getMaxSellPrice())
                .minSellPrice(commoditymarketInfo.getMinSellPrice())
                .avgSellPrice(commoditymarketInfo.getAvgSellPrice())
                .minMeanPrice(commoditymarketInfo.getMinMeanPrice())
                .maxMeanPrice(commoditymarketInfo.getMaxMeanPrice())
                .averageMeanPrice(commoditymarketInfo.getAverageMeanPrice())
                .totalStock(commoditymarketInfo.getTotalStock())
                .totalDemand(commoditymarketInfo.getTotalDemand())
                .totalStations(commoditymarketInfo.getTotalStations())
                .stationsWithBuyPrice(commoditymarketInfo.getStationsWithBuyPrice())
                .stationsWithSellPrice(commoditymarketInfo.getStationsWithSellPrice())
                .stationsWithBuyPriceLowerThanAverage(commoditymarketInfo.getStationsWithBuyPriceLowerThanAverage())
                .stationsWithSellPriceHigherThanAverage(commoditymarketInfo.getStationsWithSellPriceHigherThanAverage())
                .highestSellingToStation(mapStation(commoditymarketInfo.getHighestSellingToStation()))
                .lowestBuyingFromStation(mapStation(commoditymarketInfo.getLowestBuyingFromStation()))
                .build();
    }

    private CommodityMarketInfoResponse.Station mapStation(Station station) {
        return CommodityMarketInfoResponse.Station.builder()
                .arrivalDistance(station.getArrivalDistance())
                .name(station.getName())
                .system(CommodityMarketInfoResponse.System.builder()
                        .name(station.getSystem().getName())
                        .xCoordinate(station.getSystem().getXCoordinate())
                        .yCoordinate(station.getSystem().getYCoordinate())
                        .zCoordinate(station.getSystem().getZCoordinate())
                        .build())
                .build();
    }
}
