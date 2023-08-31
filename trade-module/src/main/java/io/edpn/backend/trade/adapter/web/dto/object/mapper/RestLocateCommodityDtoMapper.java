package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestPageInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.PageInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestLocateCommodityDtoMapper implements LocateCommodityDtoMapper {
    
    private final StationDtoMapper stationDtoMapper;
    private final ValidatedCommodityDtoMapper commodityDtoMapper;
    private final PageInfoDtoMapper pageInfoDtoMapper;
    
    @Override
    public LocateCommodityDto map(LocateCommodity locateCommodity) {
        return new RestLocateCommodityDto(
                (RestValidatedCommodityDto)commodityDtoMapper.map(locateCommodity.getValidatedCommodity()),
                (RestStationDto)stationDtoMapper.map(locateCommodity.getStation()),
                locateCommodity.getPriceUpdatedAt(),
                locateCommodity.getSupply(),
                locateCommodity.getDemand(),
                locateCommodity.getBuyPrice(),
                locateCommodity.getSellPrice(),
                locateCommodity.getDistance(),
                Optional.ofNullable(locateCommodity.getPageInfo())
                        .map(pageInfoDtoMapper::map)
        );
    }
}
