package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.domain.model.MarketDatum;
import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class DefaultFindCommodityUseCase implements FindCommodityUseCase {
    
    private final MarketDatumRepository marketDatumRepository;
    
    @Override
    public List<MarketDatum> findCommoditiesOrderByDistance(UUID commodityId) {
        return marketDatumRepository.findAllOrderByDistance(commodityId);
    }
}
