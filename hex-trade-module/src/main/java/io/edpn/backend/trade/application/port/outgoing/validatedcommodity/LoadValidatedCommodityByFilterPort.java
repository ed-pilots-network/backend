package io.edpn.backend.trade.application.port.outgoing.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.FindCommodityEntity;

import java.util.List;

public interface LoadValidatedCommodityByFilterPort {
    
    List<ValidatedCommodity> loadByFilter(FindCommodityEntity findCommodityEntity);
}
