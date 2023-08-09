package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;

import java.util.List;

public interface FindValidatedCommodityByFilterPort {
    
    List<ValidatedCommodityDTO> findByFilter(FindCommodityDTO findCommodityRequest);
    
}
