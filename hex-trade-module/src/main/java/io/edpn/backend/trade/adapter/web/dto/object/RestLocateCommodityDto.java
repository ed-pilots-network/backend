package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;

import java.time.LocalDateTime;

public record RestLocateCommodityDto(
        ValidatedCommodityDto commodity,
        StationDto station,
        LocalDateTime priceUpdatedAt,
        Long supply,
        Long demand,
        Long buyPrice,
        Long sellPrice,
        Double distance) implements LocateCommodityDto {
}
