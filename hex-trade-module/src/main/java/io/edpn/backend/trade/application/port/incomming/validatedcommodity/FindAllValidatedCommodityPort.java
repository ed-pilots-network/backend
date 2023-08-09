package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;

import java.util.List;

public interface FindAllValidatedCommodityPort {
    
    List<ValidatedCommodityDTO> findAll();
}
