package io.edpn.backend.trade.application.port.outgoing.commodity;

import io.edpn.backend.trade.application.domain.Commodity;

public interface LoadOrCreateCommodityByNamePort {
    
    Commodity loadOrCreate(String name);
}
