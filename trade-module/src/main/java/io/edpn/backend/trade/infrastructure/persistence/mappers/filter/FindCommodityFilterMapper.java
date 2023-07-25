package io.edpn.backend.trade.infrastructure.persistence.mappers.filter;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.infrastructure.persistence.filter.FindCommodityFilterPersistence;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCommodityFilterMapper {
    
    public FindCommodityFilterPersistence map(FindCommodityFilter findCommodityFilter){
        
        return FindCommodityFilterPersistence.builder()
                .type(findCommodityFilter.getType().toString())
                .isRare(findCommodityFilter.getIsRare())
                .build();
    }
}
