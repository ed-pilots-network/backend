package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisLocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisLocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityRepository implements LocateCommodityByFilterPort {

    private final MybatisLocateCommodityRepository mybatisLocateCommodityRepository;
    private final MybatisLocateCommodityEntityMapper mybatisLocateCommodityEntityMapper;
    private final MybatisLocateCommodityFilterMapper mybatisLocateCommodityFilterMapper;

    @Override
    public List<LocateCommodity> locateCommodityByFilter(io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter locateCommodityFilter) {
        return mybatisLocateCommodityRepository
                .locateCommodityByFilter((MybatisLocateCommodityFilter) mybatisLocateCommodityFilterMapper.map(locateCommodityFilter))
                .stream()
                .map(mybatisLocateCommodityEntityMapper::map)
                .toList();
    }
}
