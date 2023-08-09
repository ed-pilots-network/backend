package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisPersistenceFindCommodityFilterFilter;
import io.edpn.backend.trade.application.domain.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.mapper.PersistenceFindCommodityMapper;

public class MybatisPersistenceFindCommodityMapper implements PersistenceFindCommodityMapper {
    @Override
    public MybatisPersistenceFindCommodityFilterFilter map(FindCommodityFilter findCommodityFilter) {
        return MybatisPersistenceFindCommodityFilterFilter.builder()
                .type(findCommodityFilter.type())
                .isRare(findCommodityFilter.isRare())
                .build();
    }
}
