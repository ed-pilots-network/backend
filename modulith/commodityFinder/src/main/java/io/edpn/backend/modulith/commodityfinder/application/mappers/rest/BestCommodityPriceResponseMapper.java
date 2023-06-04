package io.edpn.backend.modulith.commodityfinder.application.mappers.rest;

import io.edpn.backend.modulith.commodityfinder.application.dto.rest.BestCommodityPriceResponse;
import io.edpn.backend.modulith.commodityfinder.domain.entity.BestCommodityPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
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
                                .arrivalDistance(0) //TODO
                                .name(station.getName())
                                .system(BestCommodityPriceResponse.System.builder()
                                        .name(station.getSystem().getName())
                                        .xCoordinate(station.getSystem().getXCoordinate())
                                        .yCoordinate(station.getSystem().getYCoordinate())
                                        .zCoordinate(station.getSystem().getZCoordinate())
                                        .build())
                                .build())
                        .toList())
                .highestStation(bestCommodityPrice.getStationEntitiesWithHighestBuyPrice().stream()
                        .map(station -> BestCommodityPriceResponse.Station.builder()
                                .arrivalDistance(0) //TODO
                                .name(station.getName())
                                .system(BestCommodityPriceResponse.System.builder()
                                        .name(station.getSystem().getName())
                                        .xCoordinate(station.getSystem().getXCoordinate())
                                        .yCoordinate(station.getSystem().getYCoordinate())
                                        .zCoordinate(station.getSystem().getZCoordinate())
                                        .build())
                                .build())
                        .toList())
                .build();
    }
}
