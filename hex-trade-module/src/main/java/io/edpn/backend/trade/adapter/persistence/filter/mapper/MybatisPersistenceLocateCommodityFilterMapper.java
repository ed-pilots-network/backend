package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;

public class MybatisPersistenceLocateCommodityFilterMapper implements PersistenceLocateCommodityFilterMapper {
    
    @Override
    public MybatisLocateCommodityFilter map(LocateCommodityFilter locateCommodityFilter) {
        return MybatisLocateCommodityFilter.builder()
                .commodityDisplayName(locateCommodityFilter.commodityDisplayName())
                .xCoordinate(locateCommodityFilter.xCoordinate())
                .yCoordinate(locateCommodityFilter.yCoordinate())
                .zCoordinate(locateCommodityFilter.zCoordinate())
                .includePlanetary(locateCommodityFilter.includePlanetary())
                .includeOdyssey(locateCommodityFilter.includeOdyssey())
                .includeFleetCarriers(locateCommodityFilter.includeFleetCarriers())
                .maxLandingPadSize(String.valueOf(locateCommodityFilter.maxLandingPadSize()))
                .minSupply(locateCommodityFilter.minSupply())
                .minDemand(locateCommodityFilter.minDemand())
                .build();
    }
}
