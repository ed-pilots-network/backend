package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ValidatedCommodityRepository implements LoadAllValidatedCommodityPort, LoadValidatedCommodityByFilterPort, LoadValidatedCommodityByNamePort {

    private final MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository;
    private final ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> mybatisValidatedCommodityEntityMapper;
    private final MybatisPersistenceFindCommodityFilterMapper mybatisPersistenceFindCommodityFilterMapper;
    
    @Override
    public List<ValidatedCommodity> loadAll() {
        return mybatisValidatedCommodityRepository
                .findAll()
                .stream()
                .map(mybatisValidatedCommodityEntityMapper::map)
                .toList();
    }
    
    @Override
    public List<ValidatedCommodity> loadByFilter(FindCommodityFilter findCommodityFilter) {
        return mybatisValidatedCommodityRepository
                .findByFilter(mybatisPersistenceFindCommodityFilterMapper.map(findCommodityFilter))
                .stream()
                .map(mybatisValidatedCommodityEntityMapper::map)
                .toList();
    }
    
    @Override
    public Optional<ValidatedCommodity> loadByName(String displayName) {
        return mybatisValidatedCommodityRepository
                .findByName(displayName)
                .map(mybatisValidatedCommodityEntityMapper::map);
    }
}
