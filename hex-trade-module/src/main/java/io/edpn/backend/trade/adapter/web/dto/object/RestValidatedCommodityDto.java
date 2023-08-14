package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import lombok.Getter;

@Getter
public record RestValidatedCommodityDto(
        String commodityName,
        String displayName,
        String type,
        Boolean isRare) implements ValidatedCommodityDto {
}
