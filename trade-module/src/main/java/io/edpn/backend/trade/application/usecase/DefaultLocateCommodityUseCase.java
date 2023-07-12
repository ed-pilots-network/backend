package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultLocateCommodityUseCase implements LocateCommodityUseCase {
    
    private final LocateCommodityRepository locateCommodityRepository;
    
    @Override
    public List<LocateCommodity> locateCommoditiesOrderByDistance(LocateCommodityFilter locateCommodityFilter) {
        return locateCommodityRepository.locateCommodityByFilter(locateCommodityFilter);
    }
}
