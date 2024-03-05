package io.edpn.backend.trade.adapter.web.dto.object;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Schema(name = "CommodityDto")
@Builder
@Jacksonized
public record ValidatedCommodityDto(
        String commodityName,
        String displayName,
        String type,
        Boolean isRare) {
}
