package io.edpn.backend.trade.application.port.outgoing.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;

import java.util.List;

public interface LoadValidatedCommodityByFilterPort {
    
    List<ValidatedCommodity> loadByFilter(FindCommodityFilter findCommodityFilter);
}
