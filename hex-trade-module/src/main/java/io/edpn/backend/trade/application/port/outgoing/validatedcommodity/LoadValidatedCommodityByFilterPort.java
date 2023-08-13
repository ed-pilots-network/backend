package io.edpn.backend.trade.application.port.outgoing.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindCommodityFilter;

import java.util.List;

public interface LoadValidatedCommodityByFilterPort {
    
    List<ValidatedCommodity> loadByFilter(PersistenceFindCommodityFilter persistenceFindCommodityFilter);
}
