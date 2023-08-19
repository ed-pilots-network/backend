package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "LocateCommodityDto")
public record RestLocateCommodityDto(
        RestValidatedCommodityDto commodity,
        RestStationDto station,
        LocalDateTime priceUpdatedAt,
        Long supply,
        Long demand,
        Long buyPrice,
        Long sellPrice,
        Double distance) implements LocateCommodityDto {
}
