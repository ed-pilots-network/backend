package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;

public class RestLocateCommodityFilterDtoMapper implements LocateCommodityFilterDtoMapper {
    @Override
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
