package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.LocateCommodityFilter;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.LocateCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.LocateCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MybatisLocateCommodityRepository implements LocateCommodityRepository {
    
    private final LocateCommodityMapper locateCommodityMapper;
    private final LocateCommodityEntityMapper locateCommodityEntityMapper;
    
    
    @Override
    public List<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter) {
        return locateCommodityEntityMapper.locateCommodityByFilter(locateCommodityFilter).stream()
                .map(locateCommodityMapper::map)
                .toList();
    }
}
