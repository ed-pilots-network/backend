package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.PageInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Optional;

@Schema(name = "LocateCommodityDto")
@Builder
@Jacksonized
public record RestLocateCommodityDto(
        RestValidatedCommodityDto commodity,
        RestStationDto station,
        LocalDateTime priceUpdatedAt,
        Long supply,
        Long demand,
        Long buyPrice,
        Long sellPrice,
        Double distance,
        Optional<? extends PageInfoDto> pageInfo) implements LocateCommodityDto {
}
