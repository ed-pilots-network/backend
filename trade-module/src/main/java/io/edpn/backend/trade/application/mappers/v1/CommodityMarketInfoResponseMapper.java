package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.v1.CoordinateDTO;
import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.model.Station;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

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
                        .coordinates(coordinateFromSystem(station.getSystem()))
                        .build())
                .build();
    }

    private CoordinateDTO coordinateFromSystem(io.edpn.backend.trade.domain.model.System system) {
        if (Objects.isNull(system.getXCoordinate())) {
            return null;
        } else {
            return CoordinateDTO.builder()
                    .xCoordinate(system.getXCoordinate())
                    .yCoordinate(system.getYCoordinate())
                    .zCoordinate(system.getZCoordinate())
                    .build();
        }
    }
}
