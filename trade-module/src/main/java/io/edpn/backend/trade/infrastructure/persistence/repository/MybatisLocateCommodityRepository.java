package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.LocateCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.LocateCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.LocateCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MybatisLocateCommodityRepository implements LocateCommodityRepository {
    
    private final LocateCommodityMapper locateCommodityMapper;
    private final LocateCommodityEntityMapper locateCommodityEntityMapper;
    private final LocateCommodityFilterMapper locateCommodityFilterMapper;
    
    
    @Override
    public List<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter) {
        System.out.println(locateCommodityFilter);
        System.out.println(locateCommodityFilterMapper.map(locateCommodityFilter));
        return locateCommodityEntityMapper.locateCommodityByFilter(locateCommodityFilterMapper.map(locateCommodityFilter))
                .stream()
                .map(locateCommodityMapper::map)
                .toList();
    }
}
