package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.LocateCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocateCommodityDtoMapper {

    private final StationDtoMapper stationDtoMapper;
    private final ValidatedCommodityDtoMapper commodityDtoMapper;

    public LocateCommodityDto map(LocateCommodity locateCommodity) {
        return new LocateCommodityDto(
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
