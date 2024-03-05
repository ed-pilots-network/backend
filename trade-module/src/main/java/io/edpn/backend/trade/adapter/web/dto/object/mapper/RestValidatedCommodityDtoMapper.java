package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;

public class RestValidatedCommodityDtoMapper {

    public RestValidatedCommodityDto map(ValidatedCommodity validatedCommodity) {
        return new RestValidatedCommodityDto(
                validatedCommodity.commodityName(),
                validatedCommodity.displayName(),
                String.valueOf(validatedCommodity.type()),
                validatedCommodity.isRare()
        );
    }
}
