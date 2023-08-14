package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityService implements LocateCommodityUseCase {
    
    private final LocateCommodityByFilterPort locateCommodityByFilterPort;
    private final PersistenceLocateCommodityFilterMapper persistenceLocateCommodityFilterMapper;
    private final LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper;
    private final LocateCommodityDtoMapper locateCommodityDtoMapper;
    
    @Override
    public List<LocateCommodityDto> locateCommodityOrderByDistance(LocateCommodityFilterDto locateCommodityFilterDto) {
        return locateCommodityByFilterPort.locateCommodityByFilter(
                persistenceLocateCommodityFilterMapper.map(locateCommodityFilterDtoMapper.map(locateCommodityFilterDto)))
                .stream()
                .map(locateCommodityDtoMapper::map)
                .toList();
    }
}
