package io.edpn.backend.exploration.application.port.outgoing.ring;

import io.edpn.backend.exploration.application.domain.Ring;

public interface SaveOrUpdateRingPort {

    Ring saveOrUpdate(Ring ring);
}
