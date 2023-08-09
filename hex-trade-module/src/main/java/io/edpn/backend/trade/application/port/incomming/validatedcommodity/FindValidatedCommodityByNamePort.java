package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;

import java.util.Optional;

public interface FindValidatedCommodityByNamePort {
    
    Optional<ValidatedCommodityDTO> findByName(String displayName);
}
