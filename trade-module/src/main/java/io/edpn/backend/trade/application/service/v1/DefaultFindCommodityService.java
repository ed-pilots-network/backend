package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.domain.service.v1.FindCommodityService;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DefaultFindCommodityService implements FindCommodityService {
    
    private final FindCommodityUseCase findCommodityUseCase;
    private final FindCommodityDTOMapper findCommodityDTOMapper;
    
    @Override
    public Optional<FindCommodityResponse> findById(UUID id) {
        return findCommodityUseCase.findById(id).map(findCommodityDTOMapper::map);
    }
    
    @Override
    public List<FindCommodityResponse> findAll() {
        return findCommodityUseCase.findAll().stream()
                .map(findCommodityDTOMapper::map)
                .toList();
    }
    
    @Override
    public List<FindCommodityResponse> findByFilter(FindCommodityRequest findCommodityRequest) {
        return findCommodityUseCase.findByFilter(findCommodityDTOMapper.map(findCommodityRequest)).stream()
                .map(findCommodityDTOMapper::map)
                .toList();
    }
}
