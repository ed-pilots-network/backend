package io.edpn.backend.commodityfinder.application.mappers;

import io.edpn.backend.commodityfinder.application.dto.BestCommodityPriceResponse;
import io.edpn.backend.commodityfinder.domain.model.BestCommodityPrice;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BestCommodityPriceResponseMapper {

    public BestCommodityPriceResponse map(BestCommodityPrice bestCommodityPrice) {
        return BestCommodityPriceResponse.builder()
                .commodityName(bestCommodityPrice.getCommodity().getName())
                .buyPrice(bestCommodityPrice.getMaxBuyPrice())
                .sellPrice(bestCommodityPrice.getMinSellPrice())
                .averagePrice(bestCommodityPrice.getAveragePrice())
                .stationsThatBuy(bestCommodityPrice.getPercentStationsWithBuyPrice())
                .stationsThatBuyAboveAverage(bestCommodityPrice.getPercentStationsWithBuyPriceAboveAverage())
                .stationsThatSell(bestCommodityPrice.getPercentStationsWithSellPrice())
                .stationsThatSellBelowAverage(bestCommodityPrice.getPercentStationsWithSellPriceBelowAverage())
                .lowestSellingStation(bestCommodityPrice.getStationEntitiesWithLowestSellPrice().stream()
                        .map(station -> BestCommodityPriceResponse.Station.builder()
                                .arrivalDistance(station.getArrivalDistance())
                                .name(station.getName())
                                .system(BestCommodityPriceResponse.System.builder()
                                        .name(station.getSystem().getName())
                                        .xCoordinate(station.getSystem().getXCoordinate())
                                        .yCoordinate(station.getSystem().getYCoordinate())
                                        .zCoordinate(station.getSystem().getZCoordinate())
                                        .build())
                                .build())
                        .collect(Collectors.toCollection(LinkedList::new)))
                .highestStation(bestCommodityPrice.getStationEntitiesWithHighestBuyPrice().stream()
                        .map(station -> BestCommodityPriceResponse.Station.builder()
                                .arrivalDistance(station.getArrivalDistance())
                                .name(station.getName())
                                .system(BestCommodityPriceResponse.System.builder()
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
