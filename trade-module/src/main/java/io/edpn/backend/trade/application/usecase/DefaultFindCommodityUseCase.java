package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.domain.repository.ValidatedCommodityRepository;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultFindCommodityUseCase implements FindCommodityUseCase {
    
    private final ValidatedCommodityRepository validatedCommodityRepository;
    
    @Override
    public Optional<ValidatedCommodity> findByName(String displayName) {
        return validatedCommodityRepository.findByName(displayName);
    }
    
    @Override
    public List<ValidatedCommodity> findAll() {
        return validatedCommodityRepository.findAll();
    }
    
    @Override
    public List<ValidatedCommodity> findByFilter(FindCommodityFilter findCommodityFilter) {
        return validatedCommodityRepository.findByFilter(findCommodityFilter);
    }
}
