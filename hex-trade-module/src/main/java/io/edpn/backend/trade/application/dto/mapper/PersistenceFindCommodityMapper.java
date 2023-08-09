package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.domain.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.PersistenceFindCommodityFilter;

public interface PersistenceFindCommodityMapper {

    PersistenceFindCommodityFilter map(FindCommodityFilter findCommodityFilter);

}
