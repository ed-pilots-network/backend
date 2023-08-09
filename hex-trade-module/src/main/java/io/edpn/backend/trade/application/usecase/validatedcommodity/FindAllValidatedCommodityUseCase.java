package io.edpn.backend.trade.application.usecase.validatedcommodity;

import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;

import java.util.List;

public interface FindAllValidatedCommodityUseCase {
    
    List<ValidatedCommodityDTO> findAll();
}
