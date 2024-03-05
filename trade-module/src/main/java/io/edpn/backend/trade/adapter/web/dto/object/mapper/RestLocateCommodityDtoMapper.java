package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestLocateCommodityDtoMapper {

    private final RestStationDtoMapper restStationDtoMapper;
    private final RestValidatedCommodityDtoMapper commodityDtoMapper;

    public RestLocateCommodityDto map(LocateCommodity locateCommodity) {
        return new RestLocateCommodityDto(
                commodityDtoMapper.map(locateCommodity.validatedCommodity()),
                restStationDtoMapper.map(locateCommodity.station()),
                locateCommodity.priceUpdatedAt(),
                locateCommodity.supply(),
                locateCommodity.demand(),
                locateCommodity.buyPrice(),
                locateCommodity.sellPrice(),
                locateCommodity.distance()

        );
    }
}
