package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.LocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.LocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityRepository implements LocateCommodityByFilterPort {

    private final MybatisLocateCommodityRepository mybatisLocateCommodityRepository;
    private final LocateCommodityEntityMapper locateCommodityEntityMapper;
    private final LocateCommodityFilterMapper locateCommodityFilterMapper;

    @Override
    public List<LocateCommodity> locateCommodityByFilter(io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter locateCommodityFilter) {
        return mybatisLocateCommodityRepository
                .locateCommodityByFilter((LocateCommodityFilter) locateCommodityFilterMapper.map(locateCommodityFilter))
                .stream()
                .map(locateCommodityEntityMapper::map)
                .toList();
    }
}
