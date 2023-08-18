package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestLocateCommodityDtoMapper implements LocateCommodityDtoMapper {
    
    private final StationDtoMapper stationDtoMapper;
    private final ValidatedCommodityDtoMapper commodityDtoMapper;
    
    @Override
    public LocateCommodityDto map(LocateCommodity locateCommodity) {
        return new RestLocateCommodityDto(
                commodityDtoMapper.map(locateCommodity.validatedCommodity()),
                stationDtoMapper.map(locateCommodity.station()),
                locateCommodity.priceUpdatedAt(),
                locateCommodity.supply(),
                locateCommodity.demand(),
                locateCommodity.buyPrice(),
                locateCommodity.sellPrice(),
                locateCommodity.distance()
                
        );
    }
}
