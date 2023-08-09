package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisFindCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisFindCommodityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.FindCommodityEntity;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ValidatedCommodityRepository implements LoadAllValidatedCommodityPort, LoadValidatedCommodityByFilterPort, LoadValidatedCommodityByNamePort {

    private final MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository;
    private final MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper;
    
    @Override
    public List<ValidatedCommodity> loadAll() {
        return mybatisValidatedCommodityRepository
                .findAll()
                .stream()
                .map(mybatisValidatedCommodityEntityMapper::map)
                .toList();
    }
    
    @Override
    public List<ValidatedCommodity> loadByFilter(FindCommodityEntity findCommodityEntity) {
        return mybatisValidatedCommodityRepository
                .findByFilter((MybatisFindCommodityFilter) findCommodityEntity)
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
