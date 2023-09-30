package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;

public class MybatisPersistenceLocateCommodityFilterMapper implements PersistenceLocateCommodityFilterMapper {
    
    @Override
    public MybatisLocateCommodityFilter map(LocateCommodityFilter locateCommodityFilter) {
        return MybatisLocateCommodityFilter.builder()
                .commodityDisplayName(locateCommodityFilter.getCommodityDisplayName())
                .xCoordinate(locateCommodityFilter.getXCoordinate())
                .yCoordinate(locateCommodityFilter.getYCoordinate())
                .zCoordinate(locateCommodityFilter.getZCoordinate())
                .includePlanetary(locateCommodityFilter.getIncludePlanetary())
                .includeOdyssey(locateCommodityFilter.getIncludeOdyssey())
                .includeFleetCarriers(locateCommodityFilter.getIncludeFleetCarriers())
                .maxLandingPadSize(String.valueOf(locateCommodityFilter.getMaxLandingPadSize()))
                .minSupply(locateCommodityFilter.getMinSupply())
                .minDemand(locateCommodityFilter.getMinDemand())
                .build();
    }
}
