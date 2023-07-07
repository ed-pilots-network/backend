package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.domain.service.v1.FindCommodityService;
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
