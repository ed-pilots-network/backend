package io.edpn.backend.trade.application.dto.web.object;

public interface CommodityMarketInfoDto {
    
    ValidatedCommodityDto commodity();
    
    Double maxBuyPrice();
    
    Double minBuyPrice();
    
    Double avgBuyPrice();
    
    Double maxSellPrice();
    
    Double minSellPrice();
    
    Double avgSellPrice();
    
    Double minMeanPrice();
    
    Double maxMeanPrice();
    
    Double averageMeanPrice();
    
    Long totalStock();
    
    Long totalDemand();
    
    Integer totalStations();
    
    Integer stationsWithBuyPrice();
    
    Integer stationsWithSellPrice();
    
    Integer stationsWithBuyPriceLowerThanAverage();
    
    Integer stationsWithSellPriceHigherThanAverage();
    
    StationDto highestSellingToStation();
    
    StationDto lowestBuyingFromStation();
}
