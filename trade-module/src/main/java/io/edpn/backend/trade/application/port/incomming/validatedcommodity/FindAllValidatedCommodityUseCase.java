package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;

import java.util.List;

public interface FindAllValidatedCommodityUseCase {
    
    List<ValidatedCommodity> findAll();
}
