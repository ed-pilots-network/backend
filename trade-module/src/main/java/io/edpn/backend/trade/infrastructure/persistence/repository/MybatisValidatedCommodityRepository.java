package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.domain.repository.ValidatedCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.ValidatedCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.FindCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.ValidatedCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisValidatedCommodityRepository implements ValidatedCommodityRepository {
    
    private final ValidatedCommodityMapper validatedCommodityMapper;
    private final ValidatedCommodityEntityMapper validatedCommodityEntityMapper;
    private final FindCommodityFilterMapper findCommodityFilterMapper;
    
    
    @Override
    public Optional<ValidatedCommodity> findById(UUID id) {
        return validatedCommodityEntityMapper.findById(id)
                .map(validatedCommodityMapper::map);
    }
    
    @Override
    public List<ValidatedCommodity> findAll() {
        return validatedCommodityEntityMapper.findAll()
                .stream().map(validatedCommodityMapper::map)
                .toList();
    }
    
    @Override
    public List<ValidatedCommodity> findByFilter(FindCommodityFilter findCommodityFilter) {
        return validatedCommodityEntityMapper.findByFilter(findCommodityFilterMapper.map(findCommodityFilter))
                .stream().map(validatedCommodityMapper::map)
                .toList();
    }
}
