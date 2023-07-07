package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import io.edpn.backend.trade.application.mappers.FindCommodityDTOMapper;
import io.edpn.backend.trade.domain.service.FindCommodityService;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultFindCommodityService implements FindCommodityService {
    
    private final FindCommodityUseCase findCommodityUseCase;
    private final FindCommodityDTOMapper findCommodityDTOMapper;
    
    @Override
    public List<FindCommodityResponse> findCommoditiesNearby(FindCommodityRequest findCommodityRequest) {
        return findCommodityUseCase
                .findCommoditiesOrderByDistance(findCommodityDTOMapper.map(findCommodityRequest))
                .stream()
                .map(findCommodityDTOMapper::map)
                .toList();
    }
}
