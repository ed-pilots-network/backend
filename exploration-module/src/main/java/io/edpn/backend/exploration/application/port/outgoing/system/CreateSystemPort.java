package io.edpn.backend.exploration.application.port.outgoing.system;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

public interface CreateSystemPort {

    System create(String systemName) throws DatabaseEntityNotFoundException;
}
