package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisLocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityRepository implements LocateCommodityByFilterPort {
    
    private final MybatisLocateCommodityRepository mybatisLocateCommodityRepository;
    private final MybatisLocateCommodityEntityMapper mybatisLocateCommodityEntityMapper;
    private final MybatisPersistenceLocateCommodityFilterMapper mybatisPersistenceLocateCommodityFilterMapper;
    
    @Override
    public List<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter) {
        return mybatisLocateCommodityRepository
                .locateCommodityByFilter(mybatisPersistenceLocateCommodityFilterMapper.map(locateCommodityFilter))
                .stream()
                .map(mybatisLocateCommodityEntityMapper::map)
                .toList();
    }
}
