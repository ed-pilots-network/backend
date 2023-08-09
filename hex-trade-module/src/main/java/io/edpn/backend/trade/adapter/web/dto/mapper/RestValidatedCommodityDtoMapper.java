package io.edpn.backend.trade.adapter.web.dto.mapper;

import io.edpn.backend.trade.adapter.web.dto.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDtoMapper;

public class RestValidatedCommodityDtoMapper implements ValidatedCommodityDtoMapper {
    @Override
    public ValidatedCommodityDto map(ValidatedCommodity validatedCommodity) {
        return new RestValidatedCommodityDto(
                validatedCommodity.commodityName(),
                validatedCommodity.displayName(),
                String.valueOf(validatedCommodity.type()),
                validatedCommodity.isRare()
        );
    }
}
