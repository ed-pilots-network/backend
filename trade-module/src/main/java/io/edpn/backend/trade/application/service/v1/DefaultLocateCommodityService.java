package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.LocateCommodityDTOMapper;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultLocateCommodityService implements LocateCommodityService {
    
    private final LocateCommodityUseCase locateCommodityUseCase;
    private final LocateCommodityDTOMapper locateCommodityDTOMapper;
    
    @Override
    public List<LocateCommodityResponse> locateCommoditiesOrderByDistance(LocateCommodityRequest locateCommodityRequest) {
        return locateCommodityUseCase
                .locateCommoditiesOrderByDistance(locateCommodityDTOMapper.map(locateCommodityRequest))
                .stream()
                .map(locateCommodityDTOMapper::map)
                .toList();
    }
}
