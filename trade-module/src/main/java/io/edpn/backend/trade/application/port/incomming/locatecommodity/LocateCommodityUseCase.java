package io.edpn.backend.trade.application.port.incomming.locatecommodity;

import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;

import java.util.List;

public interface LocateCommodityUseCase {
    
    List<LocateCommodityDto> locateCommodityOrderByDistance(LocateCommodityFilterDto locateCommodityFilterDto);
    
}
