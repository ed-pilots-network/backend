package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.CommodityType;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;

import java.util.Optional;

public class FindCommodityDTOMapper {
    
    public FindCommodityResponse map(ValidatedCommodity validatedCommodity) {
        return FindCommodityResponse.builder()
                .id(validatedCommodity.getId())
                .commodityName(validatedCommodity.getCommodityName())
                .displayName(validatedCommodity.getDisplayName())
                .type(validatedCommodity.getType().toString())
                .isRare(validatedCommodity.getIsRare())
                .build();
    }
    
    public FindCommodityFilter map(FindCommodityRequest findCommodityRequest) {
        if (findCommodityRequest.getType() == null) {
            return FindCommodityFilter.builder()
                    .isRare(findCommodityRequest.getIsRare())
                    .build();
        } else {
            return FindCommodityFilter.builder()
                    .type(CommodityType.valueOf(findCommodityRequest.getType()))
                    .isRare(findCommodityRequest.getIsRare())
                    .build();
        }
    }
}
