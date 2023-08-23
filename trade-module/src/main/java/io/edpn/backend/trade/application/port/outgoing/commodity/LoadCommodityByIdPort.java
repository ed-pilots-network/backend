package io.edpn.backend.trade.application.port.outgoing.commodity;

import io.edpn.backend.trade.application.domain.Commodity;

import java.util.Optional;
import java.util.UUID;

public interface LoadCommodityByIdPort {
    
    Optional<Commodity> loadById(UUID id);
}
