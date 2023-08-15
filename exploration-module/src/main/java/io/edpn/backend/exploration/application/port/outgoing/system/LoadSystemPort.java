package io.edpn.backend.exploration.application.port.outgoing.system;

import io.edpn.backend.exploration.application.domain.System;

import java.util.Optional;

public interface LoadSystemPort {

    Optional<System> load(String name);
}
