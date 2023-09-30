package io.edpn.backend.exploration.application.port.outgoing.system;

import io.edpn.backend.exploration.application.domain.System;

public interface SaveOrUpdateSystemPort {

    System saveOrUpdate(System system);
}
