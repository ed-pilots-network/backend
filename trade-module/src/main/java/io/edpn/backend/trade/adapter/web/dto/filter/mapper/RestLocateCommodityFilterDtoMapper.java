package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.PageFilterDtoMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestLocateCommodityFilterDtoMapper implements LocateCommodityFilterDtoMapper {
    private final PageFilterDtoMapper pageFilterDtoMapper;

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
                getShipSize(locateCommodityFilterDto),
                locateCommodityFilterDto.minSupply(),
                locateCommodityFilterDto.minDemand(),
                pageFilterDtoMapper.map(locateCommodityFilterDto)
        );
    }

    private static LandingPadSize getShipSize(LocateCommodityFilterDto locateCommodityFilterDto) {
        return Optional.ofNullable(locateCommodityFilterDto.shipSize())
                .map(LandingPadSize::valueOf)
                .orElse(null);
    }
}
