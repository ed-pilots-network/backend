package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.domain.model.FindCommodityFilter;
import io.edpn.backend.trade.domain.repository.FindCommodityRepository;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultFindCommodityUseCase implements FindCommodityUseCase {
    
    private final FindCommodityRepository findCommodityRepository;
    
    @Override
    public List<FindCommodity> findCommoditiesOrderByDistance(FindCommodityFilter findCommodityFilter) {
        return findCommodityRepository.findCommodityByFilter(findCommodityFilter);
    }
}
