package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.dto.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDto;

import java.util.List;

public interface FindValidatedCommodityByFilterUseCase {
    
    List<ValidatedCommodityDto> findByFilter(FindCommodityFilterDto findCommodityRequest);
    
}
