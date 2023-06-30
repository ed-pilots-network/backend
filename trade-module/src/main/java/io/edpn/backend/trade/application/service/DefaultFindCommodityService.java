package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import io.edpn.backend.trade.application.mappers.FindCommodityMapper;
import io.edpn.backend.trade.application.usecase.DefaultFindCommodityUseCase;
import io.edpn.backend.trade.domain.service.FindCommodityService;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class DefaultFindCommodityService implements FindCommodityService {
    
    private final FindCommodityUseCase findCommodityUseCase;
    private final FindCommodityMapper findCommodityMapper;
    
    
    @Override
    public List<FindCommodityResponse> findCommoditiesNearby(FindCommodityRequest findCommodityRequest) {
        return findCommodityUseCase
                .findCommoditiesOrderByDistance(findCommodityRequest.getCommodity().getId())
                .stream()
                .map(findCommodityMapper::map)
                .toList();
    }
}
