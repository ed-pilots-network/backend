package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistencePageFilterMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MybatisPersistenceLocateCommodityFilterMapper implements PersistenceLocateCommodityFilterMapper {
    private final PersistencePageFilterMapper pageFilterMapper;

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
                .shipSize(Optional.ofNullable(locateCommodityFilter.getShipSize()).map(Enum::name).orElse(null))
                .minSupply(locateCommodityFilter.getMinSupply())
                .minDemand(locateCommodityFilter.getMinDemand())
                .page(Optional.ofNullable(locateCommodityFilter.getPageFilter()).map(pageFilterMapper::map).orElse(null))
                .build();
    }
}
