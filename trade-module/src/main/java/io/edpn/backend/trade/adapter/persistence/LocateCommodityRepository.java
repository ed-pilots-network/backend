package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityRepository implements LocateCommodityByFilterPort {
    
    private final MybatisLocateCommodityRepository mybatisLocateCommodityRepository;
    private final LocateCommodityEntityMapper<MybatisLocateCommodityEntity> mybatisLocateCommodityEntityMapper;
    private final PersistenceLocateCommodityFilterMapper mybatisPersistenceLocateCommodityFilterMapper;
    
    @Override
    public List<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter) {
        return mybatisLocateCommodityRepository
                .locateCommodityByFilter((MybatisLocateCommodityFilter) mybatisPersistenceLocateCommodityFilterMapper.map(locateCommodityFilter))
                .stream()
                .map(mybatisLocateCommodityEntityMapper::map)
                .toList();
    }
}
