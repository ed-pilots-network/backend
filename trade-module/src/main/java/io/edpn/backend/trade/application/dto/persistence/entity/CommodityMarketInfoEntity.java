package io.edpn.backend.trade.application.dto.persistence.entity;

public interface CommodityMarketInfoEntity {
    
    ValidatedCommodityEntity getValidatedCommodity();
    
    Double getMaxBuyPrice();
    
    Double getMinBuyPrice();
    
    Double getAvgBuyPrice();
    
    Double getMaxSellPrice();
    
    Double getMinSellPrice();
    
    Double getAvgSellPrice();
    
    Double getMinMeanPrice();
    
    Double getMaxMeanPrice();
    
    Double getAverageMeanPrice();
    
    Long getTotalStock();
    
    Long getTotalDemand();
    
    Integer getTotalStations();
    
    Integer getStationsWithBuyPrice();
    
    Integer getStationsWithSellPrice();
    
    Integer getStationsWithBuyPriceLowerThanAverage();
    
    Integer getStationsWithSellPriceHigherThanAverage();
    
    StationEntity getHighestSellingToStation();
    
    StationEntity getLowestBuyingFromStation();
}
