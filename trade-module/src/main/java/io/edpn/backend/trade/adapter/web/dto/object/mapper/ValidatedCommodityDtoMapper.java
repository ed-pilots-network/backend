package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;

public class ValidatedCommodityDtoMapper {

    public ValidatedCommodityDto map(ValidatedCommodity validatedCommodity) {
        return new ValidatedCommodityDto(
                validatedCommodity.commodityName(),
                validatedCommodity.displayName(),
                String.valueOf(validatedCommodity.type()),
                validatedCommodity.isRare()
        );
    }
}
