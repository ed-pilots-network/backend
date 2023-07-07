package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.domain.model.FindCommodityFilter;
import io.edpn.backend.trade.domain.repository.FindCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.FindCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.FindCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MybatisFindCommodityRepository implements FindCommodityRepository {
    
    private final FindCommodityMapper findCommodityMapper;
    private final FindCommodityEntityMapper findCommodityEntityMapper;
    
    
    @Override
    public List<FindCommodity> findCommodityByFilter(FindCommodityFilter findCommodityFilter) {
        return findCommodityEntityMapper.findCommodityByFilter(findCommodityFilter).stream()
                .map(findCommodityMapper::map)
                .toList();
    }
}
