package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindCommodityFilterMapper;

public class MybatisPersistenceFindCommodityFilterMapper implements PersistenceFindCommodityFilterMapper {
    
    @Override
    public MybatisFindCommodityFilter map(FindCommodityFilter findCommodityFilter) {
        return MybatisFindCommodityFilter.builder()
                .type(findCommodityFilter.getType())
                .isRare(findCommodityFilter.getIsRare())
                .build();
    }
}
