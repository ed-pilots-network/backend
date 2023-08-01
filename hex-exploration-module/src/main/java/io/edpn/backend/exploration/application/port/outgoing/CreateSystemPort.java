package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.domain.System;

public interface CreateSystemPort {

    System create(String systemName);
}
