package io.edpn.backend.trade.application.port.outgoing.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;

import java.util.List;

public interface LoadAllValidatedCommodityPort {
    
    List<ValidatedCommodity> loadAll();
}
