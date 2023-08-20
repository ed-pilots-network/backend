package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Schema(name = "CommodityDto")
@Builder
@Jacksonized
public record RestValidatedCommodityDto(
        String commodityName,
        String displayName,
        String type,
        Boolean isRare) implements ValidatedCommodityDto {
}
