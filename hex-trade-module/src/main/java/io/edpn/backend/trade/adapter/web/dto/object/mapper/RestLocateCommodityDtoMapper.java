package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestLocateCommodityDtoMapper implements LocateCommodityDtoMapper {
    
    private final StationDtoMapper stationDtoMapper;
    
    @Override
    public LocateCommodityDto map(LocateCommodity locateCommodity) {
        return new RestLocateCommodityDto(
                locateCommodity.validatedCommodity().displayName(),
                stationDtoMapper.map(locateCommodity.station()),
                locateCommodity.system().name(),
                locateCommodity.pricesUpdatedAt(),
                locateCommodity.supply(),
                locateCommodity.demand(),
                locateCommodity.buyPrice(),
                locateCommodity.sellPrice(),
                locateCommodity.distance()
                
        );
    }
}
