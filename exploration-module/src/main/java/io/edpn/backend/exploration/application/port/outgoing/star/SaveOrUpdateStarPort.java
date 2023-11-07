package io.edpn.backend.exploration.application.port.outgoing.star;

import io.edpn.backend.exploration.application.domain.Star;

public interface SaveOrUpdateStarPort {
    
    Star saveOrUpdate(Star star);
}
