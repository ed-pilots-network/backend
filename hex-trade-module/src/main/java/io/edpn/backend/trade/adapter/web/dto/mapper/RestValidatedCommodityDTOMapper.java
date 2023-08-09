package io.edpn.backend.trade.adapter.web.dto.mapper;

import io.edpn.backend.trade.adapter.web.dto.RestValidatedCommodityDTO;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDTOMapper;

public class RestValidatedCommodityDTOMapper implements ValidatedCommodityDTOMapper {
    @Override
    public ValidatedCommodityDTO map(ValidatedCommodity validatedCommodity) {
        return new RestValidatedCommodityDTO(
                validatedCommodity.commodityName(),
                validatedCommodity.displayName(),
                String.valueOf(validatedCommodity.type()),
                validatedCommodity.isRare()
        );
    }
}
