package io.edpn.backend.commodityfinder.application.mappers;

import io.edpn.backend.commodityfinder.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.commodityfinder.domain.model.CommodityMarketInfo;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommodityMarketInfoResponseMapper {

    public CommodityMarketInfoResponse map(CommodityMarketInfo commoditymarketInfo) {
        return CommodityMarketInfoResponse.builder()
                .commodityName(commoditymarketInfo.getCommodity().getName())
                .buyPrice(commoditymarketInfo.getMaxBuyPrice())
                .sellPrice(commoditymarketInfo.getMinSellPrice())
                .averagePrice(commoditymarketInfo.getAveragePrice())
                .stationsThatBuy(commoditymarketInfo.getPercentStationsWithBuyPrice())
                .stationsThatBuyAboveAverage(commoditymarketInfo.getPercentStationsWithBuyPriceAboveAverage())
                .stationsThatSell(commoditymarketInfo.getPercentStationsWithSellPrice())
                .stationsThatSellBelowAverage(commoditymarketInfo.getPercentStationsWithSellPriceBelowAverage())
                .lowestSellingStation(commoditymarketInfo.getStationEntitiesWithLowestSellPrice().stream()
                        .map(station -> CommodityMarketInfoResponse.Station.builder()
                                .arrivalDistance(station.getArrivalDistance())
                                .name(station.getName())
                                .system(CommodityMarketInfoResponse.System.builder()
                                        .name(station.getSystem().getName())
                                        .xCoordinate(station.getSystem().getXCoordinate())
                                        .yCoordinate(station.getSystem().getYCoordinate())
                                        .zCoordinate(station.getSystem().getZCoordinate())
                                        .build())
                                .build())
                        .collect(Collectors.toCollection(LinkedList::new)))
                .highestStation(commoditymarketInfo.getStationEntitiesWithHighestBuyPrice().stream()
                        .map(station -> CommodityMarketInfoResponse.Station.builder()
                                .arrivalDistance(station.getArrivalDistance())
                                .name(station.getName())
                                .system(CommodityMarketInfoResponse.System.builder()
                                        .name(station.getSystem().getName())
                                        .xCoordinate(station.getSystem().getXCoordinate())
                                        .yCoordinate(station.getSystem().getYCoordinate())
                                        .zCoordinate(station.getSystem().getZCoordinate())
                                        .build())
                                .build())
                        .collect(Collectors.toCollection(LinkedList::new)))
                .build();
    }
}
