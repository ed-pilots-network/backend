package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;

public class LocateCommodityFilterDtoMapper {
    public LocateCommodityFilter map(LocateCommodityFilterDto locateCommodityFilterDto) {
        return new LocateCommodityFilter(
                locateCommodityFilterDto.commodityDisplayName(),
                locateCommodityFilterDto.xCoordinate(),
                locateCommodityFilterDto.yCoordinate(),
                locateCommodityFilterDto.zCoordinate(),
                locateCommodityFilterDto.includePlanetary(),
                locateCommodityFilterDto.includeOdyssey(),
                locateCommodityFilterDto.includeFleetCarriers(),
                LandingPadSize.valueOf(locateCommodityFilterDto.maxLandingPadSize()),
                locateCommodityFilterDto.minSupply(),
                locateCommodityFilterDto.minDemand()
        );
    }
}
