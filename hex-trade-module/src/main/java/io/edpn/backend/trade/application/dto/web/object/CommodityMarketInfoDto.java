package io.edpn.backend.trade.application.dto.web.object;

import io.edpn.backend.trade.application.domain.Station;

public interface CommodityMarketInfoDto {
    
    String getCommodityDisplayName();
    
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
    
    Station getHighestSellingToStation();
    
    Station getLowestBuyingFromStation();
}
