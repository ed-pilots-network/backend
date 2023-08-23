package io.edpn.backend.trade.application.port.outgoing.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;

import java.util.Optional;

public interface LoadValidatedCommodityByNamePort {
    
    Optional<ValidatedCommodity> loadByName(String displayName);
}
