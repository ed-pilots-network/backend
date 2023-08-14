package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisLocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceLocateCommodityFilter;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityRepository implements LocateCommodityByFilterPort {
    
    private final MybatisLocateCommodityRepository mybatisLocateCommodityRepository;
    private final MybatisLocateCommodityEntityMapper mybatisLocateCommodityEntityMapper;
    
    @Override
    public List<LocateCommodity> locateCommodityByFilter(PersistenceLocateCommodityFilter locateCommodityFilter) {
        return mybatisLocateCommodityRepository
                .locateCommodityByFilter((MybatisLocateCommodityFilter) locateCommodityFilter)
                .stream()
                .map(mybatisLocateCommodityEntityMapper::map)
                .toList();
    }
}
