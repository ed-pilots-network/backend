package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocateCommodityService implements LocateCommodityUseCase {

    private final LocateCommodityByFilterPort locateCommodityByFilterPort;

    @Override
    public List<LocateCommodity> locateCommodityOrderByDistance(LocateCommodityFilter locateCommodityFilterDto) {
        return locateCommodityByFilterPort.locateCommodityByFilter(locateCommodityFilterDto);
    }
}
