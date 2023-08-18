package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.StationDto;

import java.time.LocalDateTime;

public record RestLocateCommodityDto(
        String commodityDisplayName,
        StationDto station,
        String systemName,
        LocalDateTime pricesUpdatedAt,
        Long supply,
        Long demand,
        Long buyPrice,
        Long sellPrice,
        Double distance) implements LocateCommodityDto {
}
