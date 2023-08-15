package io.edpn.backend.exploration.application.port.outgoing.system;

import io.edpn.backend.exploration.application.domain.System;

import java.util.List;

public interface LoadSystemsByNameContainingPort {

    List<System> loadByNameContaining(String name, int amount);

}
