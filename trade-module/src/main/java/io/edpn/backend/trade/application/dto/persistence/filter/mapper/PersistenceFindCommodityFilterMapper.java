package io.edpn.backend.trade.application.dto.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindCommodityFilter;

public interface PersistenceFindCommodityFilterMapper {

    PersistenceFindCommodityFilter map(FindCommodityFilter findCommodityFilter);

}
