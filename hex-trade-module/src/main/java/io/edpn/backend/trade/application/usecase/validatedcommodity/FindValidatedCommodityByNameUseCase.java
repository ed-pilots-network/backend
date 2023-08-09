package io.edpn.backend.trade.application.usecase.validatedcommodity;

import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;

import java.util.Optional;

public interface FindValidatedCommodityByNameUseCase {
    
    Optional<ValidatedCommodityDTO> findByName(String displayName);
}
