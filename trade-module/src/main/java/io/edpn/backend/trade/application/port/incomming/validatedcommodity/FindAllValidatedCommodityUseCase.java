package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;

import java.util.List;

public interface FindAllValidatedCommodityUseCase {
    
    List<ValidatedCommodityDto> findAll();
}
