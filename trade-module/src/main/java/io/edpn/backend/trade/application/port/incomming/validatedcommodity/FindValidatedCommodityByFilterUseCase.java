package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;

import java.util.List;

public interface FindValidatedCommodityByFilterUseCase {

    List<ValidatedCommodity> findByFilter(FindCommodityFilter findCommodityRequest);

}
