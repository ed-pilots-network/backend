package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;

import java.util.List;

public interface FindValidatedCommodityByFilterUseCase {
    
    List<ValidatedCommodityDto> findByFilter(FindCommodityFilterDto findCommodityRequest);
    
}
