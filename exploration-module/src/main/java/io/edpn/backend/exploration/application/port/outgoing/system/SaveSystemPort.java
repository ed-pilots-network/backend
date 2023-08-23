package io.edpn.backend.exploration.application.port.outgoing.system;

import io.edpn.backend.exploration.application.domain.System;

public interface SaveSystemPort {

    System save(System system);
}
